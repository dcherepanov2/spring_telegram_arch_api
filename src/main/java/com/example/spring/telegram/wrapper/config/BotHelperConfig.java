package com.example.spring.telegram.wrapper.config;

import com.example.spring.telegram.wrapper.definer.DefaultMessageHelper;
import com.example.spring.telegram.wrapper.definer.MessageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotHelperConfig {

    @Bean
    MessageHelper messageHelper(){
        return new DefaultMessageHelper();
    }
}
