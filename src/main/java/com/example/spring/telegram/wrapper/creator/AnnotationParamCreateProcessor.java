package com.example.spring.telegram.wrapper.creator;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.Method;

public interface AnnotationParamCreateProcessor {

    AnnotationParamCreateProcessor addCreator(BotParamCreatorChain fillerChain);

    Object[] process(Update message, Method method);
}
