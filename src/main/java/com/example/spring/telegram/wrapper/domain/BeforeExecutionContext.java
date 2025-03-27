package com.example.spring.telegram.wrapper.domain;

import lombok.Builder;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Getter
@Builder
public class BeforeExecutionContext {

    private Update message;

    private String url;

    private User user;
}
