package com.example.aop_spring_telegram.definers;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

@UtilityClass
public class MessageDefinerUtils {

    public static String defineUrl(Update update) {
        CallbackQuery callbackQuery = getCallbackQuery(update);
        Message message = getMessage(update);

        return callbackQuery != null ?
                callbackQuery.getData() :
                message.getText();
    }

    public static Long getUserId(Update update) {
        User user = getUser(update);
        return Optional.ofNullable(user)
                .map(User::getId)
                .orElseThrow();
    }

    private static User getUser(Update update) {

        CallbackQuery callbackQuery = getCallbackQuery(update);
        Message message = getMessage(update);

        return callbackQuery != null ?
                callbackQuery.getFrom():
                message.getFrom();
    }

    public static String getText(Update update) {
        Message message = getMessage(update);
        CallbackQuery callbackQuery = getCallbackQuery(update);
        return Optional.ofNullable(message)
                .map(MessageDefinerUtils::getText)
                .orElse(getText(callbackQuery));
    }

    private static Message getMessage(Update update) {
        return update.getMessage();
    }

    private static String getText(Message message) {
        return message.getText();
    }

    private static String getText(CallbackQuery callbackQuery) {
        Message message = getMessage(callbackQuery);
        return getText(message);
    }

    private static Message getMessage(CallbackQuery callbackQuery) {
        return callbackQuery.getMessage();
    }

    private static CallbackQuery getCallbackQuery(Update update) {
        return update.getCallbackQuery();
    }
}
