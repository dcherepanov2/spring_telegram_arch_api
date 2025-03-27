package com.example.spring.telegram.wrapper.config;

import com.example.spring.telegram.wrapper.annotation.BotHandler;
import com.example.spring.telegram.wrapper.annotation.BotParamCreator;
import com.example.spring.telegram.wrapper.creator.BotParamCreatorChain;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BotScanner {

    private ApplicationContext applicationContext;

    public List<Object> botHandlers() {
        return applicationContext.getBeansWithAnnotation(BotHandler.class)
                .values()
                .stream()
                .filter(this::isBotHandlerHandler)
                .toList();
    }

    public Map<Class<? extends Annotation>, BotParamCreatorChain> createHandlerStrategyMap() {
        return applicationContext.getBeansWithAnnotation(BotParamCreator.class)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> {
                            BotParamCreator strategy = entry.getValue().getClass().getAnnotation(BotParamCreator.class);
                            return strategy.relationTo();
                        },
                        entry -> (BotParamCreatorChain) entry
                ));
    }

    protected boolean isBotHandlerHandler(Object handler) {
        return Optional.ofNullable(handler)
                .map(Object::getClass)
                .map(clazz -> clazz.getAnnotation(BotHandler.class))
                .isPresent();
    }
}
