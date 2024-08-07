package com.example.aop_spring_telegram.runner;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotInvoker {

    BotApiMethod<?> invoke(Update update);
}
