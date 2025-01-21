package com.example.spring.telegram.wrapper.definer;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public interface MessageHelper {

    String defineUrl(Update update);

    Long getUserId(Update update);

    User getUser(Update update);

    String getText(Update update);
}
