package com.example.spring.telegram.wrapper.runner;

import com.example.spring.telegram.wrapper.definer.BotHandlerApplierDefiner;
import com.example.spring.telegram.wrapper.definer.MessageHelper;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;



@Service
public class DefaultBotInvoker implements BotInvoker {

    private final BotHandlerApplierDefiner botHandlerApplierDefiner;

    private final MessageHelper defaultMessageHelper;

    public DefaultBotInvoker(
            BotHandlerApplierDefiner botHandlerApplierDefiner,
            MessageHelper defaultMessageHelper
    ) {
        this.botHandlerApplierDefiner = botHandlerApplierDefiner;
        this.defaultMessageHelper = defaultMessageHelper;
    }

    @Override
    public BotApiMethod<?> invoke(Update update) {
        var url = defaultMessageHelper.defineUrl(update);
        return Optional.ofNullable(url)
                .map(botHandlerApplierDefiner::defineApplier)
                .map(applier -> applier.apply(url))
                .filter(message -> message.isSend())
                .map(message -> message.getMessage())
                .orElse(null);
    }
}
