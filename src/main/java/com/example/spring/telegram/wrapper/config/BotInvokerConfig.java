package com.example.spring.telegram.wrapper.config;

import com.example.spring.telegram.wrapper.creator.AnnotationParamCreateProcessor;
import com.example.spring.telegram.wrapper.helper.BotHandlerHelper;
import com.example.spring.telegram.wrapper.execution.before.BeforeExecutionProcessor;
import com.example.spring.telegram.wrapper.runner.DefaultBotInvoker;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotInvokerConfig {

    private BotHandlerHelper botHandlerHelper;
    private AnnotationParamCreateProcessor processor;
    private final BeforeExecutionProcessor beforeExecutionProcessor;

    public BotInvokerConfig(BeforeExecutionProcessor beforeExecutionProcessor) {
        this.beforeExecutionProcessor = beforeExecutionProcessor;
    }

    DefaultBotInvoker defaultBotInvoker(){
        return new DefaultBotInvoker(botHandlerHelper, processor, beforeExecutionProcessor);
    }
}
