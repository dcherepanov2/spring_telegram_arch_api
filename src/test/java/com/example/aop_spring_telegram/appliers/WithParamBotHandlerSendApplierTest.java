package com.example.aop_spring_telegram.appliers;

import com.example.aop_spring_telegram.annotations.BotHandler;
import com.example.aop_spring_telegram.test.clazz.TestClassBotHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WithParamBotHandlerSendApplierTest {

    @Mock
    private ApplicationContext applicationContext;

    @InjectMocks
    private WithParamBotHandlerSendApplier withParamBotHandlerApplier;

    @Test
    void whenCallApplyContainsTargetUrlThenReturnSendMessageCorrectly() {
        when(applicationContext.getBeansWithAnnotation(BotHandler.class))
                .thenReturn(createMapNameBean());

        var sendMessage = withParamBotHandlerApplier.apply("/test/class/send-message-with-param?send_message=send-message");

        assertEquals("send-message", sendMessage.getText());
    }

    private Map<String, Object> createMapNameBean() {
        return Map.of("testClassBotHandler", new TestClassBotHandler());
    }
}
