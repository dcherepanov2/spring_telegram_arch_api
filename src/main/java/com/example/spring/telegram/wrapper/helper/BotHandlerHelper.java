package com.example.spring.telegram.wrapper.helper;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface BotHandlerHelper {

    Method defineMethodBy(Update message, Class<? extends Annotation>... annotations);

    Object findSuitableHandler(Update message);
}
