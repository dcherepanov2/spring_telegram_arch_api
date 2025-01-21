//package com.example.spring.telegram.wrapper.applier;
//
//import com.example.spring.telegram.wrapper.annotation.BotHandler;
//import com.example.spring.telegram.wrapper.test.clazz.TestClassBotHandler;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.context.ApplicationContext;
//
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class BotHandlerWithParamApplierTest {
//
//    @Mock
//    private ApplicationContext applicationContext;
//
//    @InjectMocks
//    private BotWithParamApplier botHandlerWithParamApplier;
//
//    @Test
//    void whenCallApplyContainsTargetUrlThenReturnSendMessageCorrectly() {
//        when(applicationContext.getBeansWithAnnotation(BotHandler.class))
//                .thenReturn(createMapNameBean());
//
//        var sendMessage = botHandlerWithParamApplier.apply("/test/class/send-message-with-param?send_message=send-message");
//
//        assertEquals("send-message", sendMessage.getText());
//    }
//
//    private Map<String, Object> createMapNameBean() {
//        return Map.of("testClassBotHandler", new TestClassBotHandler());
//    }
//}
