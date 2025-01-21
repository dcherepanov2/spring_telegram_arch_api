package com.example.spring.telegram.wrapper.applier;

import com.example.spring.telegram.wrapper.domain.Message;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface BotApplier<T extends BotApiMethod<?>> {

    Message<T> apply(String url);
}
