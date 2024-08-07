package com.example.aop_spring_telegram.definers;

import com.example.aop_spring_telegram.appliers.BotHandlerSendApplier;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface BotHandlerApplierDefiner {

    BotHandlerSendApplier<? extends BotApiMethod<?>> defineApplier(String url);
}
