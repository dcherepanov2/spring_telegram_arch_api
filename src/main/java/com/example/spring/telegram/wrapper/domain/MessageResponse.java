package com.example.spring.telegram.wrapper.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

@Getter
@AllArgsConstructor
public class MessageResponse {

    private final BotApiMethod<?> message;

    private final boolean isSend;
}
