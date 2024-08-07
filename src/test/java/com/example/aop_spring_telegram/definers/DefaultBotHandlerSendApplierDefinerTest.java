package com.example.aop_spring_telegram.definers;

import com.example.aop_spring_telegram.annotations.BotHandlerStrategy;
import com.example.aop_spring_telegram.appliers.BotHandlerSendApplier;
import com.example.aop_spring_telegram.appliers.DefaultBotHandlerApplier;
import com.example.aop_spring_telegram.appliers.WithParamBotHandlerApplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultBotHandlerSendApplierDefinerTest {

    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private StrategyDefinerHelper strategyDefinerHelper;
    @InjectMocks
    private DefaultBotHandlerApplierDefiner defaultBotHandlerApplierDefiner;

    @Test
    void whenCallDefineApplierUrlWithoutParamThenCallDefaultStrategy() throws Exception {
        var mapNameBean = createNameBeanMap();
        when(applicationContext.getBeansWithAnnotation(BotHandlerStrategy.class))
                .thenReturn(mapNameBean);
        PowerMockito.when(defaultBotHandlerApplierDefiner, "getAllAppliers")
                .thenReturn(createNameBeanMap());

        var botHandlerApplier = defaultBotHandlerApplierDefiner.defineApplier("/send-message");

        assertInstanceOf(DefaultBotHandlerApplier.class, botHandlerApplier);
    }

    @Test
    void whenCallDefineApplierUrlWithParamThenCallDefaultStrategy() {
        when(applicationContext.getBeansWithAnnotation(BotHandlerStrategy.class))
                .thenReturn(createNameBeanMap());

        BotHandlerSendApplier<? extends BotApiMethod<?>> botHandlerSendApplier =
                defaultBotHandlerApplierDefiner.defineApplier("/send-message?test-param=test-param");

        assertInstanceOf(WithParamBotHandlerApplier.class, botHandlerSendApplier);
    }

    private Map<String, Object> createNameBeanMap() {
        return Map.of(
                "defaultBotHandlerApplier", new DefaultBotHandlerApplier(null),
                "withParamBotHandlerApplier", new WithParamBotHandlerApplier(null)
        );
    }
}
