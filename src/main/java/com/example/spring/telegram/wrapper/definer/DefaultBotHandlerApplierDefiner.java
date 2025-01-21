package com.example.spring.telegram.wrapper.definer;

import com.example.spring.telegram.wrapper.applier.BotApplier;
import com.example.spring.telegram.wrapper.strategy.BotStrategyType;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import com.example.spring.telegram.wrapper.exception.ApplierNotFoundException;

import java.util.Map;
import java.util.Optional;


@Service
public class DefaultBotHandlerApplierDefiner implements BotHandlerApplierDefiner {

    private final Map<BotStrategyType, BotApplier<? extends BotApiMethod<?>>> appliers;

    public DefaultBotHandlerApplierDefiner(Map<BotStrategyType, BotApplier<? extends BotApiMethod<?>>> appliers) {
        this.appliers = appliers;
    }

    @Override
    public BotApplier<? extends BotApiMethod<?>> defineApplier(String url) {
        return Optional.ofNullable(url)
                .map(BotStrategyType::defineStrategyType)
                .map(appliers::get)
                .orElseThrow(ApplierNotFoundException::new);
    }
}
