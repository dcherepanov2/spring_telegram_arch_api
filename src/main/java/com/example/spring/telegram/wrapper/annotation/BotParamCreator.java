package com.example.spring.telegram.wrapper.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Component
@Retention(RetentionPolicy.RUNTIME)
public @interface BotParamCreator {

    Class<? extends Annotation> relationTo();
}
