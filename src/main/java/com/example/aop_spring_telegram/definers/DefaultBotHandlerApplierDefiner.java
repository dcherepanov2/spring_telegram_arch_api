package com.example.aop_spring_telegram.definers;

import com.example.aop_spring_telegram.annotations.BotHandlerStrategy;
import com.example.aop_spring_telegram.exceptions.ApplierNotFoundException;
import com.example.aop_spring_telegram.appliers.BotHandlerSendApplier;
import com.example.aop_spring_telegram.strategy.BotStrategyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

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
