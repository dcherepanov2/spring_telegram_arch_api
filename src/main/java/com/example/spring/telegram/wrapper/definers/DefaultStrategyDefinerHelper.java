package com.example.spring.telegram.wrapper.definers;

import com.example.spring.telegram.wrapper.annotations.BotHandlerStrategy;
import com.example.spring.telegram.wrapper.strategy.BotStrategyType;
import org.springframework.stereotype.Service;

@Service
class DefaultStrategyDefinerHelper implements StrategyDefinerHelper {

    public BotStrategyType getStrategy(Object selector) {
        BotHandlerStrategy annotation = selector.getClass().getAnnotation(BotHandlerStrategy.class);
        return annotation.strategy();
    }
}
