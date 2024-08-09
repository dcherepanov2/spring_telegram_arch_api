package com.example.spring.telegram.wrapper.appliers;

import com.example.spring.telegram.wrapper.annotations.BotHandler;
import com.example.spring.telegram.wrapper.annotations.BotRequestMapping;
import com.example.spring.telegram.wrapper.exceptions.BotHandlerNotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

@Service
abstract class AbstractBotHandlerApplier<T extends BotApiMethod<?>> {

    private final ApplicationContext applicationContext;

    protected AbstractBotHandlerApplier(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    protected Stream<Object> findAllHandlers(String url) {
        return applicationContext.getBeansWithAnnotation(BotHandler.class)
                .values()
                .stream()
                .filter(handler -> isSuitableHandler(handler, url));
    }

    @SafeVarargs
    protected final Stream<Method> findAllMethodsContainsAnnotations(String url, Class<? extends Annotation>... annotations) {
        return findAllHandlers(url)
                .flatMap(handler -> Arrays.stream(handler.getClass().getDeclaredMethods()))
                .filter(method -> Arrays.stream(annotations)
                        .anyMatch(method::isAnnotationPresent)
                )
                .filter(method -> isSuitableMethod(method, url));
    }

    @SuppressWarnings("unchecked")
    protected T invoke(Object handler, Method method, Object... objects) {
        try {
            return (T) method.invoke(handler, objects);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    protected void invoke(Object handler, Method method) {
        try {
            method.invoke(handler);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    protected boolean isSuitableHandler(Object handler, String targetUrl) {
        var botHandler = handler.getClass().getAnnotation(BotHandler.class);
        return botHandler != null && targetUrl.contains(botHandler.url());
    }

    protected boolean isSuitableMethod(Method method, String targetUrl) {
        var botRequestMapping = method.getAnnotation(BotRequestMapping.class);
        return botRequestMapping != null && targetUrl.contains(botRequestMapping.url());
    }

    protected Object defineHandler(String url) {
        return findAllHandlers(url)
                .findFirst()
                .orElseThrow(BotHandlerNotFoundException::new);
    }
}
