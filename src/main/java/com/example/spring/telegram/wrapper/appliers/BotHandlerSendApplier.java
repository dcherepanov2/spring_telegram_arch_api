package com.example.spring.telegram.wrapper.appliers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface BotHandlerSendApplier<T extends BotApiMethod<?>> {

    T apply(String url);
}
