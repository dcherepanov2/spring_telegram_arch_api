package com.example.spring.telegram.wrapper.domain;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public class MessageRequest {

    private final Update message;

    private final String url;
}
