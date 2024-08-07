package com.example.aop_spring_telegram.runner;

import com.example.aop_spring_telegram.appliers.BotHandlerSendApplier;
import com.example.aop_spring_telegram.appliers.DefaultBotHandlerSendApplier;
import com.example.aop_spring_telegram.definers.BotHandlerApplierDefiner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultBotInvokerTest {

    @Mock
    private BotHandlerApplierDefiner botHandlerApplierDefiner;

    @InjectMocks
    private DefaultBotInvoker defaultBotInvoker;

    @Test
    void whenCallInvokeThenReturnSendMessageCorrectly() {
        var botHandlerSendApplier = createBotHandlerSendApplier();
        when(botHandlerApplierDefiner.defineApplier("/send-message"))
                .thenReturn(botHandlerSendApplier);

        var sendMessage = (SendMessage) defaultBotInvoker.invoke(any());

        assertEquals("send-message-without-params", sendMessage.getText());
    }

    private BotHandlerSendApplier<? extends BotApiMethod<?>> createBotHandlerSendApplier() {
        return new DefaultBotHandlerSendApplier(null);
    }
}
