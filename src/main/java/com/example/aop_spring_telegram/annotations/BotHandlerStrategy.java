package com.example.aop_spring_telegram.annotations;

import com.example.aop_spring_telegram.strategy.BotStrategyType;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface BotHandlerStrategy {

    BotStrategyType strategy();
}
