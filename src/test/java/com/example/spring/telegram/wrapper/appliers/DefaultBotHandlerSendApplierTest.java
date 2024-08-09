package com.example.spring.telegram.wrapper.appliers;

import com.example.spring.telegram.wrapper.annotations.BotHandler;
import com.example.spring.telegram.wrapper.annotations.BotRequestMapping;
import com.example.spring.telegram.wrapper.test.clazz.TestClassBotHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultBotHandlerSendApplierTest {

    @Mock
    private ApplicationContext applicationContext;

    @InjectMocks
    private DefaultBotHandlerSendApplier defaultBotHandlerApplier;

    @Test
    void whenFindAllHandlersContainsTargetUrlThenReturnClassCorrectly() {
        when(applicationContext.getBeansWithAnnotation(BotHandler.class))
                .thenReturn(createMapNameBean());

        var handler = defaultBotHandlerApplier.findAllHandlers("/test/class/send-message")
                .findFirst()
                .orElseThrow();

        assertInstanceOf(TestClassBotHandler.class, handler);
    }

    @Test
    void whenFindAllMethodsContainsTargetUrlThenReturnMethodCorrectly() {
        when(applicationContext.getBeansWithAnnotation(BotHandler.class))
                .thenReturn(createMapNameBean());

        var method = defaultBotHandlerApplier.findAllMethodsContainsAnnotations("/test/class/send-message", BotRequestMapping.class)
                .findFirst()
                .orElseThrow();

        assertEquals("testSendMessage", method.getName());
    }

    private Map<String, Object> createMapNameBean() {
        return Map.of("testClassBotHandler", new TestClassBotHandler());
    }
}
