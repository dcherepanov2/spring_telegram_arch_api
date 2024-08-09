package com.example.spring.telegram.wrapper.appliers;

import com.example.spring.telegram.wrapper.annotations.BotHandlerStrategy;
import com.example.spring.telegram.wrapper.annotations.BotParam;
import com.example.spring.telegram.wrapper.annotations.BotRequestMapping;
import com.example.spring.telegram.wrapper.exceptions.ApplierNotFoundException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.spring.telegram.wrapper.strategy.BotStrategyType.WITH_PARAMS;

@BotHandlerStrategy(strategy = WITH_PARAMS)
public class WithParamBotHandlerSendApplier extends AbstractBotHandlerApplier<SendMessage> implements BotHandlerSendApplier<SendMessage> {

    public WithParamBotHandlerSendApplier(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    public SendMessage apply(String url) {
        var handler = defineHandler(url);
        return findAllMethodsContainsAnnotations(url, BotRequestMapping.class)
                .findFirst()
                .map(method -> invoke(handler, method, defineParams(url, method)))
                .orElseThrow(ApplierNotFoundException::new);
    }

    private Object[] defineParams(String url, Method method) {
        var urlParams = extractUrlParams(url);
        var injectParams = new ArrayList<>();
        var methodParams = method.getParameters();
        Arrays.stream(methodParams).forEachOrdered(parameter -> {
            var botParam = parameter.getAnnotation(BotParam.class);
            if (botParam != null) {
                var value = urlParams.get(botParam.name());
                injectParams.add(value);
            }
        });
        return injectParams.toArray();
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
        return Optional.ofNullable(method.getAnnotation(BotRequestMapping.class))
                .map(BotRequestMapping::url)
                .filter(targetUrl::contains)
                .filter(url -> containsBotParamAnnotation(method))
                .isPresent();
    }

    private boolean containsBotParamAnnotation(Method method) {
        return Arrays.stream(method.getParameters())
                .anyMatch(parameter -> parameter.isAnnotationPresent(BotParam.class));
    }
}
