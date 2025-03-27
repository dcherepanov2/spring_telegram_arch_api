package com.example.spring.telegram.wrapper.config;

import com.example.spring.telegram.wrapper.helper.BotHandlerHelper;
import com.example.spring.telegram.wrapper.helper.DefaultBotHandlerHelper;
import com.example.spring.telegram.wrapper.helper.DefaultMessageHelper;
import com.example.spring.telegram.wrapper.helper.MessageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BotHelperConfig {

    private List<Object> botHandlers;

    @Bean
    MessageHelper messageHelper(){
        return new DefaultMessageHelper();
    }

    @Bean
    BotHandlerHelper botHandlerHelper(MessageHelper messageHelper){
        return new DefaultBotHandlerHelper(botHandlers, messageHelper);
    }
}
