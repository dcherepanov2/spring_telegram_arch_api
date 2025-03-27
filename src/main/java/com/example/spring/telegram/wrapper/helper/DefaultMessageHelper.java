package com.example.spring.telegram.wrapper.helper;

import org.jvnet.hk2.annotations.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

@Service
public class DefaultMessageHelper implements MessageHelper{

    @Override
    public String defineUrl(Update update) {
        CallbackQuery callbackQuery = getCallbackQuery(update);
        Message message = getMessage(update);
        return callbackQuery != null ?
                callbackQuery.getData() :
                message.getText();
    }

    @Override
    public Long getUserId(Update update) {
        return Optional.ofNullable(update)
                .map(this::getUser)
                .map(User::getId)
                .orElseThrow();
    }

    @Override
    public User getUser(Update update) {

        CallbackQuery callbackQuery = getCallbackQuery(update);
        Message message = getMessage(update);

        return callbackQuery != null ?
                callbackQuery.getFrom():
                message.getFrom();
    }

    @Override
    public String getText(Update update) {
        Message message = getMessage(update);
        CallbackQuery callbackQuery = getCallbackQuery(update);
        return Optional.ofNullable(message)
                .map(this::getText)
                .orElse(getText(callbackQuery));
    }

    private String getText(Message message) {
        return message.getText();
    }

    private String getText(CallbackQuery callbackQuery) {
        Message message = getMessage(callbackQuery);
        return getText(message);
    }

    private Message getMessage(Update update) {
        return update.getMessage();
    }

    private Message getMessage(CallbackQuery callbackQuery) {
        return callbackQuery.getMessage();
    }

    private CallbackQuery getCallbackQuery(Update update) {
        return update.getCallbackQuery();
    }
}
