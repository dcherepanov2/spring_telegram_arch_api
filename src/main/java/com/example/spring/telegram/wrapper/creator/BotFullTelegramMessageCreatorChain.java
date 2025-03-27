package com.example.spring.telegram.wrapper.creator;

import com.example.spring.telegram.wrapper.annotation.BotParamCreator;
import com.example.spring.telegram.wrapper.annotation.FullTelegramMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.Parameter;

@BotParamCreator(relationTo = FullTelegramMessage.class)
public class BotFullTelegramMessageCreatorChain implements BotParamCreatorChain {

    @Override
    public Object create(Update message, Parameter param) {
        return message;
    }
}
