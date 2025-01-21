package com.example.spring.telegram.wrapper.applier;

import com.example.spring.telegram.wrapper.domain.Message;
import com.example.spring.telegram.wrapper.exception.ApplierNotFoundException;
import com.example.spring.telegram.wrapper.annotation.BotHandlerStrategy;
import com.example.spring.telegram.wrapper.annotation.BotRequestMapping;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

import static com.example.spring.telegram.wrapper.strategy.BotStrategyType.DEFAULT;


@BotHandlerStrategy(strategy = DEFAULT)
public class BotWithoutParamApplier extends AbstractBotHandlerApplier<SendMessage>
        implements BotApplier<SendMessage> {

    public BotWithoutParamApplier(List<Object> botHandlers) {
        super(botHandlers);
    }

    @Override
    public Message<SendMessage> apply(String url) {
        var handler = defineHandler(url);
        return findAllMethodsContainsAnnotations(url, BotRequestMapping.class)
                .findAny()
                .map(method -> invoke(handler, method, new Object[]{}))
                .orElseThrow(ApplierNotFoundException::new);
    }
}
