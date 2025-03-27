package com.example.spring.telegram.wrapper.helper;

import com.example.spring.telegram.wrapper.annotation.BotHandler;
import com.example.spring.telegram.wrapper.annotation.BotRequestMapping;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class DefaultBotHandlerHelper implements BotHandlerHelper {

    private final List<Object> botHandlers;
    private final MessageHelper messageHelper;

    public DefaultBotHandlerHelper(List<Object> botHandlers, MessageHelper messageHelper) {
        this.botHandlers = botHandlers;
        this.messageHelper = messageHelper;
    }

    @SafeVarargs
    public final Method defineMethodBy(Update message, Class<? extends Annotation>... annotations) {
        return Optional.ofNullable(message)
                .map(this::findSuitableHandler)
                .map(handler -> Arrays.stream(handler.getClass().getDeclaredMethods()))
                .map(Stream::toList)
                .stream()
                .flatMap(List::stream)
                .filter(method -> this.isAnnotationPresent(method, annotations))
                .filter(method -> isSuitableMethod(method, messageHelper.defineUrl(message)))
                .findAny()
                .orElseThrow();
    }

    @Override
    public Object findSuitableHandler(Update message) {
        String url = messageHelper.defineUrl(message);
        return botHandlers.stream()
                .filter(botHandler -> isHandler(botHandler, url))
                .findAny()
                .orElseThrow();
    }

    @SafeVarargs
    private boolean isAnnotationPresent(Method method, Class<? extends Annotation>... annotations) {
        return Arrays.stream(annotations)
                .anyMatch(method::isAnnotationPresent);
    }

    private boolean isHandler(Object handler, String targetUrl) {
        var botHandler = handler.getClass().getAnnotation(BotHandler.class);
        return botHandler != null && targetUrl.contains(botHandler.url());
    }

    private boolean isSuitableMethod(Method method, String targetUrl) {
        var botRequestMapping = method.getAnnotation(BotRequestMapping.class);
        return botRequestMapping != null && targetUrl.contains(botRequestMapping.url());
    }
}
