package com.example.spring.telegram.wrapper.runner;

import com.example.spring.telegram.wrapper.annotation.BotRequestMapping;
import com.example.spring.telegram.wrapper.domain.BotHandlerContext;
import com.example.spring.telegram.wrapper.domain.MessageResponse;
import com.example.spring.telegram.wrapper.creator.AnnotationParamCreateProcessor;
import com.example.spring.telegram.wrapper.helper.BotHandlerHelper;
import com.example.spring.telegram.wrapper.processor.before.BeforeExecutionProcessor;
import com.example.spring.telegram.wrapper.processor.before.chain.BeforeExecutionChain;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static com.example.spring.telegram.wrapper.enumuration.SenderStrategy.SEND;

@Service
public class DefaultBotInvoker implements BotInvoker {

    private final BotHandlerHelper botHandlerHelper;

    private final AnnotationParamCreateProcessor paramCreateProcessor;

    private final BeforeExecutionProcessor beforeExecutionProcessor;

    public DefaultBotInvoker(
            BotHandlerHelper botHandlerHelper,
            AnnotationParamCreateProcessor annotationParamCreateProcessor,
            BeforeExecutionProcessor beforeExecutionProcessor
    ) {
        this.botHandlerHelper = botHandlerHelper;
        this.paramCreateProcessor = annotationParamCreateProcessor;
        this.beforeExecutionProcessor = beforeExecutionProcessor;
    }

    @Override
    public BotApiMethod<?> invoke(Update message) {
        BotHandlerContext botHandlerContext = buildHandlerContext(message);
        beforeExecutionProcessor.process(botHandlerContext);
        MessageResponse messageResponse = invoke(botHandlerContext);
        return messageResponse.isSend()? messageResponse.getMessage(): null;
    }

    private BotHandlerContext buildHandlerContext(Update message) {
        Object suitableHandler = botHandlerHelper.findSuitableHandler(message);
        Method method = botHandlerHelper.defineMethodBy(message, BotRequestMapping.class);
        return BotHandlerContext.builder()
                .handler(suitableHandler)
                .method(method)
                .message(message)
                .build();
    }

    private MessageResponse invoke(BotHandlerContext handlerContext) {
        try {
            Method method = handlerContext.getMethod();
            Update message = handlerContext.getMessage();
            Object handler = handlerContext.getHandler();

            Object[] valueParameters = paramCreateProcessor.process(message, method);
            BotApiMethod<?> returnObject = (BotApiMethod<?>) method.invoke(handler, method, valueParameters);

            return buildMessage(returnObject, method);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private MessageResponse buildMessage(BotApiMethod<?> message, Method method) {
        var botRequestMapping = method.getAnnotation(BotRequestMapping.class);
        return new MessageResponse(message, botRequestMapping.returnStrategy() == SEND);
    }
}
