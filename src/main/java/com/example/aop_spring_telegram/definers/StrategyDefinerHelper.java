package com.example.aop_spring_telegram.definers;

import com.example.aop_spring_telegram.strategy.BotStrategyType;

public interface StrategyDefinerHelper {

    BotStrategyType getStrategy(Object selector);
}
