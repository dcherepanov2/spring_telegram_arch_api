package com.example.spring.telegram.wrapper.domain;

import lombok.Builder;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.Method;

@Getter
@Builder
public class BotHandlerContext {

    private Object handler;

    private Method method;

    private Update message;
}
