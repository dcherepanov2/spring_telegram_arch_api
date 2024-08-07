package com.example.aop_spring_telegram.appliers;


import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface BotHandlerSendApplier<T extends BotApiMethod<?>> {

    T apply(String url);
}
