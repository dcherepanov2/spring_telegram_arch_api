package com.example.spring.telegram.wrapper.applier;

import com.example.spring.telegram.wrapper.annotation.BotHandler;
import com.example.spring.telegram.wrapper.annotation.BotRequestMapping;
import com.example.spring.telegram.wrapper.domain.Message;
import com.example.spring.telegram.wrapper.exception.BotHandlerNotFoundException;
import com.example.spring.telegram.wrapper.strategy.SenderStrategy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
class AbstractBotHandlerApplier<T extends BotApiMethod<?>> {

    private final List<Object> botHandlers;

    protected AbstractBotHandlerApplier(List<Object> botHandlers) {
        this.botHandlers = botHandlers;
    }

    @SafeVarargs
    public final Stream<Method> findAllMethodsContainsAnnotations(String url, Class<? extends Annotation>... annotations) {
        return findSuitableHandler(url)
                .flatMap(handler -> Arrays.stream(handler.getClass().getDeclaredMethods()))
                .filter(method -> Arrays.stream(annotations)
                        .anyMatch(method::isAnnotationPresent)
                )
                .filter(method -> isSuitableMethod(method, url));
    }

    @SuppressWarnings("unchecked")
    public Message<T> invoke(Object handler, Method method, Object... objects) {
        try {
            T returnObject = (T) method.invoke(handler, objects);
            return buildMessage(returnObject, method);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void invoke(Object handler, Method method) {
        try {
            method.invoke(handler);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isSuitableMethod(Method method, String targetUrl) {
        var botRequestMapping = method.getAnnotation(BotRequestMapping.class);
        return botRequestMapping != null && targetUrl.contains(botRequestMapping.url());
    }

    public Object defineHandler(String url) {
        return findSuitableHandler(url)
                .findAny()
                .orElseThrow(BotHandlerNotFoundException::new);
    }

    public Message<T> buildMessage(T message, Method method){
        var botRequestMapping = method.getAnnotation(BotRequestMapping.class);
        return new Message<>(message, botRequestMapping.returnStrategy() == SenderStrategy.SEND);
    }

    public boolean isHandler(Object handler, String targetUrl) {
        var botHandler = handler.getClass().getAnnotation(BotHandler.class);
        return botHandler != null && targetUrl.contains(botHandler.url());
    }

    private Stream<Object> findSuitableHandler(String url){
        return botHandlers.stream()
                .filter(botHandler -> isHandler(botHandler, url));
    }
}
