package com.example.spring.telegram.wrapper.config.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

@Component
public class RedisPropertyCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String propertyValue = context.getEnvironment().getProperty("redis.enabled");
        return Boolean.parseBoolean(propertyValue);
    }
}