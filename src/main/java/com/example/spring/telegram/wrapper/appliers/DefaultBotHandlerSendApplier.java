package com.example.spring.telegram.wrapper.appliers;

import com.example.spring.telegram.wrapper.exceptions.ApplierNotFoundException;
import com.example.spring.telegram.wrapper.annotations.BotHandlerStrategy;
import com.example.spring.telegram.wrapper.annotations.BotRequestMapping;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.spring.telegram.wrapper.strategy.BotStrategyType.DEFAULT;


@BotHandlerStrategy(strategy = DEFAULT)
public class DefaultBotHandlerSendApplier extends AbstractBotHandlerApplier<SendMessage>
        implements BotHandlerSendApplier<SendMessage> {

    public DefaultBotHandlerSendApplier(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    public SendMessage apply(String url) {
        var handler = defineHandler(url);
        return findAllMethodsContainsAnnotations(url, BotRequestMapping.class)
                .findFirst()
                .map(method -> invoke(handler, method, new Object[]{}))
                .orElseThrow(ApplierNotFoundException::new);
    }
}
