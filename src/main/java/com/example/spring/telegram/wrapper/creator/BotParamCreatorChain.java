package com.example.spring.telegram.wrapper.creator;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.Parameter;
import java.util.List;

public interface BotParamCreatorChain {

    Object create(Update message, Parameter param);
}
