package com.example.spring.telegram.wrapper.definers;

import com.example.spring.telegram.wrapper.annotations.BotHandlerStrategy;
import com.example.spring.telegram.wrapper.appliers.BotHandlerSendApplier;
import com.example.spring.telegram.wrapper.strategy.BotStrategyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import com.example.spring.telegram.wrapper.exceptions.ApplierNotFoundException;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class DefaultBotHandlerApplierDefiner implements BotHandlerApplierDefiner {

    private final ApplicationContext applicationContext;
    private final StrategyDefinerHelper strategyDefinerHelper;
    private final Map<BotStrategyType, BotHandlerSendApplier<? extends BotApiMethod<?>>> appliers;

    @Autowired
    public DefaultBotHandlerApplierDefiner(
            ApplicationContext applicationContext,
            StrategyDefinerHelper strategyDefinerHelper
    ) {
        this.applicationContext = applicationContext;
        this.strategyDefinerHelper = strategyDefinerHelper;
        this.appliers = getAllAppliers();
    }

    @Override
    public BotHandlerSendApplier<? extends BotApiMethod<?>> defineApplier(String url) {
        return Optional.ofNullable(url)
                .map(BotStrategyType::defineStrategyType)
                .map(appliers::get)
                .orElseThrow(ApplierNotFoundException::new);
    }

    private Map<BotStrategyType, BotHandlerSendApplier<? extends BotApiMethod<?>>> getAllAppliers() {
        return applicationContext.getBeansWithAnnotation(BotHandlerStrategy.class)
                .values()
                .stream()
                .collect(Collectors.toMap(
                        strategyDefinerHelper::getStrategy,
                        applier -> (BotHandlerSendApplier<? extends BotApiMethod<?>>) applier
                ));
    }
}
