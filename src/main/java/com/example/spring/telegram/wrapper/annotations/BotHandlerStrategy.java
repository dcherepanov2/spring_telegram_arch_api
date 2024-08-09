package com.example.spring.telegram.wrapper.annotations;

import com.example.spring.telegram.wrapper.strategy.BotStrategyType;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface BotHandlerStrategy {

    BotStrategyType strategy();
}
