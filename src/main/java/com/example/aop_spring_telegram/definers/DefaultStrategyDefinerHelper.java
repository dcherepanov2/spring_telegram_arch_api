package com.example.aop_spring_telegram.definers;

import com.example.aop_spring_telegram.annotations.BotHandlerStrategy;
import com.example.aop_spring_telegram.strategy.BotStrategyType;
import org.springframework.stereotype.Service;

@Service
class DefaultStrategyDefinerHelper implements StrategyDefinerHelper {

    public BotStrategyType getStrategy(Object selector) {
        BotHandlerStrategy annotation = selector.getClass().getAnnotation(BotHandlerStrategy.class);
        return annotation.strategy();
    }
}
