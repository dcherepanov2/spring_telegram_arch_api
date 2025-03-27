package com.example.spring.telegram.wrapper.annotation;

import com.example.spring.telegram.wrapper.enumuration.UserPreviousStepFindStrategy;

import static com.example.spring.telegram.wrapper.enumuration.UserPreviousStepFindStrategy.FIND_FIRST;

public @interface UserPreviousStep {

    UserPreviousStepFindStrategy findStrategy() default FIND_FIRST;

    String url();
}
