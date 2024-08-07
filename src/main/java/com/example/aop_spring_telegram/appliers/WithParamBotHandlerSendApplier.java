package com.example.aop_spring_telegram.appliers;

import com.example.aop_spring_telegram.annotations.BotHandlerStrategy;
import com.example.aop_spring_telegram.annotations.BotParam;
import com.example.aop_spring_telegram.annotations.BotRequestMapping;
import com.example.aop_spring_telegram.exceptions.ApplierNotFoundException;
import com.example.aop_spring_telegram.exceptions.BotHandlerNotFoundException;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.aop_spring_telegram.strategy.BotStrategyType.WITH_PARAMS;

@BotHandlerStrategy(strategy = WITH_PARAMS)
public class WithParamBotHandlerSendApplier extends AbstractBotHandlerApplier<SendMessage> implements BotHandlerSendApplier<SendMessage> {

    public WithParamBotHandlerSendApplier(ApplicationContext applicationContext) {
        super(applicationContext);
    }


    @Override
    public SendMessage apply(String url) {
        var urlWithoutParam = defineUrlWithoutParam(url);
        var handler = findAllHandlers(urlWithoutParam)
                .findFirst()
                .orElseThrow(BotHandlerNotFoundException::new);
        return findAllMethodsContainsAnnotations(urlWithoutParam, BotRequestMapping.class)
                .findFirst()
                .map(method -> invoke(handler, method, defineParams(url, method)))
                .orElseThrow(ApplierNotFoundException::new);
    }

    private String defineUrlWithoutParam(String url) {
        String urlWithoutBraces = url.replaceAll("\\{.*?}", "");

        urlWithoutBraces = urlWithoutBraces.replaceAll("[&?]+$", "");
        urlWithoutBraces = urlWithoutBraces.replaceAll("[&?]{2,}", "&");

        return urlWithoutBraces;
    }

    private Object[] defineParams(String url, Method method) {
        var urlParams = extractUrlParams(url);
        var params = new ArrayList<>();
        var invokedParams = method.getParameters();
        Arrays.stream(invokedParams).forEachOrdered(parameter -> {
            BotParam botParam = parameter.getAnnotation(BotParam.class);
            if (botParam != null) {
                Object value = urlParams.get(botParam.name());
                params.add(value);
            }
        });
        return params.toArray();
    }

    public Map<String, Object> extractUrlParams(String url) {
        Map<String, Object> params = new HashMap<>();
        Pattern pattern = Pattern.compile("[?&]([^=&]+)=([^&]*)");
        Matcher matcher = pattern.matcher(url);

        while (matcher.find()) {
            String key = URLDecoder.decode(matcher.group(1), StandardCharsets.UTF_8);
            String value = URLDecoder.decode(matcher.group(2), StandardCharsets.UTF_8);
            params.put(key, value);
        }

        return params;
    }
    @Override
    protected boolean isSuitableMethod(Method method, String targetUrl) {
        var botRequestMapping = method.getAnnotation(BotRequestMapping.class);
        var urlWithoutParam = defineUrlWithoutParam(botRequestMapping.url());
        return targetUrl.contains(urlWithoutParam) && containsBotParamAnnotation(method);
    }

    private boolean containsBotParamAnnotation(Method method) {
        return Arrays.stream(method.getParameters())
                .anyMatch(parameter -> parameter.isAnnotationPresent(BotParam.class));
    }
}
