package com.example.spring.telegram.wrapper.definer;

import com.example.spring.telegram.wrapper.applier.BotApplier;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface BotHandlerApplierDefiner {

    BotApplier<? extends BotApiMethod<?>> defineApplier(String url);
}
