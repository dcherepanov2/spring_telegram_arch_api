package com.example.spring.telegram.wrapper.definers;

import com.example.spring.telegram.wrapper.appliers.BotHandlerSendApplier;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface BotHandlerApplierDefiner {

    BotHandlerSendApplier<? extends BotApiMethod<?>> defineApplier(String url);
}
