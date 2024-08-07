package com.example.aop_spring_telegram.strategy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BotStrategyTypeTest {

    @Test
    void whenUrlContainsPatternWithoutParamsThenReturnDefaultStrategy(){
        BotStrategyType strategy = Optional.of("/send-message")
                .map(BotStrategyType::defineStrategyType)
                .orElseThrow();

        assertEquals(BotStrategyType.DEFAULT, strategy);
    }

    @Test
    void whenUrlContainsPatternWithParamsThenReturnWithParamsStrategy(){
        BotStrategyType strategy = Optional.of("/send-message?test_param=test_param")
                .map(BotStrategyType::defineStrategyType)
                .orElseThrow();

        assertEquals(BotStrategyType.WITH_PARAMS, strategy);
    }
}
