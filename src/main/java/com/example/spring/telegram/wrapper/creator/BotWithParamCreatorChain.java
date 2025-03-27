package com.example.spring.telegram.wrapper.creator;

import com.example.spring.telegram.wrapper.annotation.BotParamCreator;
import com.example.spring.telegram.wrapper.annotation.BotParam;
import com.example.spring.telegram.wrapper.helper.MessageHelper;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.Parameter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@BotParamCreator(relationTo = BotParam.class)
public class BotWithParamCreatorChain implements BotParamCreatorChain {

    private final MessageHelper messageHelper;

    public BotWithParamCreatorChain(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    @Override
    public Object create(Update message, Parameter param) {
        return Optional.ofNullable(message)
                .map(messageHelper::defineUrl)
                .map(url -> fillParams(url, param))
                .orElseThrow();
    }

    private Object fillParams(String url, Parameter param) {
        var urlParams = extractUrlParams(url);
        var injectParams = new ArrayList<>();

        var botParam = param.getAnnotation(BotParam.class);
        if (botParam != null) {
            var value = urlParams.get(botParam.name());
            injectParams.add(value);
        }

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
}
