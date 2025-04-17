package com.example.spring.telegram.wrapper.annotation;

import com.example.spring.telegram.wrapper.config.*;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({
        BotParamFillerConfig.class,
        BotInvokerConfig.class,
        BotHelperConfig.class,
        RedisConfig.class,
        BeforeExecutionConfig.class,
        MainBotHandlerConfig.class
})
public @interface TelegramBotHandlingEnabled {
}
