package com.example.spring.telegram.wrapper.config;

import com.example.spring.telegram.wrapper.helper.MessageHelper;
import com.example.spring.telegram.wrapper.creator.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.util.Map;

@Configuration
public class BotParamFillerConfig {

    private MessageHelper messageHelper;
    private Map<Class<? extends Annotation>, BotParamCreatorChain> createHandlerStrategyMap;

    BotParamCreatorChain botHandlerWithoutParam(){
        return new BotFullTelegramMessageCreatorChain();
    }

    BotParamCreatorChain botHandlerWithParam(){
        return new BotWithParamCreatorChain(messageHelper);
    }

    BotParamCreatorChain userPreviousStepParam(){
        return new BotWithParamCreatorChain(messageHelper);
    }

    @Bean
    AnnotationParamCreateProcessor defaultAnnotationParamFillerTemplate(){
        return new DefaultAnnotationParamCreateProcessor(createHandlerStrategyMap)
                .addCreator(botHandlerWithoutParam())
                .addCreator(botHandlerWithParam())
                .addCreator(userPreviousStepParam());
    }
}
