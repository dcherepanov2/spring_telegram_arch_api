package com.example.aop_spring_telegram.test.utils;

import com.example.aop_spring_telegram.appliers.BotHandlerSendApplier;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.lang.reflect.Constructor;

public class ReflexClassCreatorUtils {

    private ReflexClassCreatorUtils() {
    }

    public static BotHandlerSendApplier<SendMessage> createDefaultBotHandlerApplier(ApplicationContext applicationContext){
        try {
            Class<?> clazz = Class.forName("com.example.aop_spring_telegram.appliers.DefaultBotHandlerApplier.java");
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            return (BotHandlerSendApplier<SendMessage>) constructor.newInstance(applicationContext);
        } catch (Exception e){
            throw new RuntimeException();
        }
    }
}
