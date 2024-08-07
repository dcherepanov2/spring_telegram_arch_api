package com.example.aop_spring_telegram.runner;

import com.example.aop_spring_telegram.definers.BotHandlerApplierDefiner;
import com.example.aop_spring_telegram.exceptions.ApplierNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

import static com.example.aop_spring_telegram.definers.MessageDefinerUtils.defineUrl;

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
