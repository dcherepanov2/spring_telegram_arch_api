package com.example.spring.telegram.wrapper.runner;

import com.example.spring.telegram.wrapper.definers.BotHandlerApplierDefiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.example.spring.telegram.wrapper.exceptions.ApplierNotFoundException;

import java.util.Optional;

import static com.example.spring.telegram.wrapper.definers.MessageDefinerUtils.defineUrl;


@Service
class DefaultBotInvoker implements BotInvoker{

    private final BotHandlerApplierDefiner botHandlerApplierDefiner;

    @Autowired
    public DefaultBotInvoker(BotHandlerApplierDefiner botHandlerApplierDefiner) {
        this.botHandlerApplierDefiner = botHandlerApplierDefiner;
    }

    @Override
    public BotApiMethod<?> invoke(Update update) {
        var url = defineUrl(update);
        return Optional.ofNullable(url)
                .map(botHandlerApplierDefiner::defineApplier)
                .map(applier -> applier.apply(url))
                .orElseThrow(ApplierNotFoundException::new);
    }
}
