package com.example.aop_spring_telegram.appliers;

import com.example.aop_spring_telegram.annotations.BotHandlerStrategy;
import com.example.aop_spring_telegram.annotations.BotRequestMapping;
import com.example.aop_spring_telegram.exceptions.ApplierNotFoundException;
import com.example.aop_spring_telegram.exceptions.BotHandlerNotFoundException;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.aop_spring_telegram.strategy.BotStrategyType.DEFAULT;

@BotHandlerStrategy(strategy = DEFAULT)
public class DefaultBotHandlerSendApplier extends AbstractBotHandlerApplier<SendMessage>
        implements BotHandlerSendApplier<SendMessage> {

    public DefaultBotHandlerSendApplier(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    public SendMessage apply(String url) {
        Object handler = findAllHandlers(url)
                .findFirst()
                .orElseThrow(BotHandlerNotFoundException::new);
        return findAllMethodsContainsAnnotations(url, BotRequestMapping.class)
                .findFirst()
                .map(method -> invoke(handler, method, new Object[]{}))
                .orElseThrow(ApplierNotFoundException::new);
    }
}
