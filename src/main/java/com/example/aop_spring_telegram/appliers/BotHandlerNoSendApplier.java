package com.example.aop_spring_telegram.appliers;


import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface BotHandlerNoSendApplier {

    void apply(String url);
}
