package com.example.spring.telegram.wrapper.strategy;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.function.Predicate;

@Getter
public enum BotSenderStrategyType {
    SEND_MESSAGE(defaultTextMessage());

    private final Predicate<Object> isSuitable;

    BotSenderStrategyType(Predicate<Object> isSuitable) {
        this.isSuitable = isSuitable;
    }

    private static Predicate<Object> defaultTextMessage(){
        return object -> object instanceof SendMessage;
    }
}
