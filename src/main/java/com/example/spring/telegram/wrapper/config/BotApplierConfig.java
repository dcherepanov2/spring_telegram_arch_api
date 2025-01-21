package com.example.spring.telegram.wrapper.config;

import com.example.spring.telegram.wrapper.applier.BotApplier;
import com.example.spring.telegram.wrapper.applier.BotWithParamApplier;
import com.example.spring.telegram.wrapper.applier.BotWithoutParamApplier;
import com.example.spring.telegram.wrapper.definer.BotHandlerApplierDefiner;
import com.example.spring.telegram.wrapper.definer.DefaultBotHandlerApplierDefiner;
import com.example.spring.telegram.wrapper.strategy.BotStrategyType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.List;
import java.util.Map;

import static com.example.spring.telegram.wrapper.strategy.BotStrategyType.DEFAULT;
import static com.example.spring.telegram.wrapper.strategy.BotStrategyType.WITH_PARAMS;

@Configuration
public class BotApplierConfig {

    private List<Object> botHandlers;

    @Bean
    BotHandlerApplierDefiner defaultBotHandlerApplierDefiner(){
        return new DefaultBotHandlerApplierDefiner(appliers());
    }

    @Bean
    BotWithoutParamApplier botHandlerWithoutParamApplier(){
        return new BotWithoutParamApplier(botHandlers);
    }

    @Bean
    BotWithParamApplier botHandlerWithParamApplier(){
        return new BotWithParamApplier(botHandlers);
    }

    private Map<BotStrategyType, BotApplier<? extends BotApiMethod<?>>> appliers(){
        return Map.of(
                DEFAULT, botHandlerWithoutParamApplier(),
                WITH_PARAMS, botHandlerWithParamApplier()
        );
    }
}
