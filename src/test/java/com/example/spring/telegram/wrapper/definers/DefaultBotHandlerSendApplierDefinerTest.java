package com.example.spring.telegram.wrapper.definers;

import com.example.spring.telegram.wrapper.annotations.BotHandlerStrategy;
import com.example.spring.telegram.wrapper.appliers.BotHandlerSendApplier;
import com.example.spring.telegram.wrapper.appliers.DefaultBotHandlerSendApplier;
import com.example.spring.telegram.wrapper.appliers.WithParamBotHandlerSendApplier;
import com.example.spring.telegram.wrapper.strategy.BotStrategyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultBotHandlerSendApplierDefinerTest {

    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private StrategyDefinerHelper strategyDefinerHelper;
    private DefaultBotHandlerApplierDefiner defaultBotHandlerApplierDefiner;

    @BeforeEach
    void setup(){
        var mapNameBean = createNameBeanMap();
        when(strategyDefinerHelper.getStrategy(any(DefaultBotHandlerSendApplier.class)))
                .thenReturn(BotStrategyType.DEFAULT);
        when(strategyDefinerHelper.getStrategy(any(WithParamBotHandlerSendApplier.class)))
                .thenReturn(BotStrategyType.WITH_PARAMS);
        when(applicationContext.getBeansWithAnnotation(BotHandlerStrategy.class))
                .thenReturn(mapNameBean);
    }

    @Test
    void whenCallDefineApplierUrlWithoutParamThenCallDefaultStrategy() {
        defaultBotHandlerApplierDefiner = new DefaultBotHandlerApplierDefiner(applicationContext, strategyDefinerHelper);

        var botHandlerApplier = defaultBotHandlerApplierDefiner.defineApplier("/send-message");

        assertInstanceOf(DefaultBotHandlerSendApplier.class, botHandlerApplier);
    }

    @Test
    void whenCallDefineApplierUrlWithParamThenCallDefaultStrategy() {
        defaultBotHandlerApplierDefiner = new DefaultBotHandlerApplierDefiner(applicationContext, strategyDefinerHelper);

        BotHandlerSendApplier<? extends BotApiMethod<?>> botHandlerSendApplier =
                defaultBotHandlerApplierDefiner.defineApplier("/send-message?test-param=test-param");

        assertInstanceOf(WithParamBotHandlerSendApplier.class, botHandlerSendApplier);
    }

    private Map<String, Object> createNameBeanMap() {
        return Map.of(
                "defaultBotHandlerApplier", new DefaultBotHandlerSendApplier(null),
                "withParamBotHandlerApplier", new WithParamBotHandlerSendApplier(null)
        );
    }
}
