package com.example.spring.telegram.wrapper.domain;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public record MessageResponse(BotApiMethod<?> message, boolean isSend) {

}
