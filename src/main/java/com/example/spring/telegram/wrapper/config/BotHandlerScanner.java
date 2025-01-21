package com.example.spring.telegram.wrapper.config;

import com.example.spring.telegram.wrapper.annotation.BotHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class BotHandlerScanner {

    private ApplicationContext applicationContext;

    @Bean
    List<Object> botHandlers() {
        return applicationContext.getBeansWithAnnotation(BotHandler.class)
                .values()
                .stream()
                .filter(this::isBotHandlerHandler)
                .toList();
    }

    protected boolean isBotHandlerHandler(Object handler) {
        return Optional.ofNullable(handler)
                .map(Object::getClass)
                .map(clazz -> clazz.getAnnotation(BotHandler.class))
                .isPresent();
    }
}
