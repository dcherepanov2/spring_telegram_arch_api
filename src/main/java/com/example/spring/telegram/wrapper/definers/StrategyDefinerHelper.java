package com.example.spring.telegram.wrapper.definers;


import com.example.spring.telegram.wrapper.strategy.BotStrategyType;

public interface StrategyDefinerHelper {

    BotStrategyType getStrategy(Object selector);
}
