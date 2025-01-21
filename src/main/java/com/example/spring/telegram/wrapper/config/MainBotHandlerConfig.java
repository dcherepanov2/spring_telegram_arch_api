package com.example.spring.telegram.wrapper.config;

import com.example.spring.telegram.wrapper.MainBotHandler;
import com.example.spring.telegram.wrapper.runner.BotInvoker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Configuration
public class MainBotHandlerConfig {

    private BotInvoker botInvoker;

    @Bean
    TelegramLongPollingBot mainBotHandler() throws TelegramApiException {
        return new MainBotHandler(botInvoker);
    }
}
