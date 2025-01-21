package com.example.spring.telegram.wrapper.annotation;


import com.example.spring.telegram.wrapper.strategy.SenderStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.spring.telegram.wrapper.strategy.SenderStrategy.SEND;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BotRequestMapping {

    String url();
    SenderStrategy returnStrategy() default SEND;
}
