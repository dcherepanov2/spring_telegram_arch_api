package com.example.spring.telegram.wrapper.config;

import com.example.spring.telegram.wrapper.definer.BotHandlerApplierDefiner;
import com.example.spring.telegram.wrapper.definer.MessageHelper;
import com.example.spring.telegram.wrapper.runner.DefaultBotInvoker;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotInvokerConfig {

    private MessageHelper messageHelper;

    private BotHandlerApplierDefiner defaultBotHandlerApplierDefiner;

    DefaultBotInvoker defaultBotInvoker(){
        return new DefaultBotInvoker(defaultBotHandlerApplierDefiner, messageHelper);
    }
}
