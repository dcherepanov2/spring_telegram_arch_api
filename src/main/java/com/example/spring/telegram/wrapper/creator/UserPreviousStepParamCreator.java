package com.example.spring.telegram.wrapper.creator;

import com.example.spring.telegram.wrapper.annotation.BotParamCreator;
import com.example.spring.telegram.wrapper.annotation.UserPreviousStep;
import com.example.spring.telegram.wrapper.db.service.UserStepService;
import com.example.spring.telegram.wrapper.domain.user.step.UserStep;
import com.example.spring.telegram.wrapper.enumuration.UserPreviousStepFindStrategy;
import com.example.spring.telegram.wrapper.helper.MessageHelper;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Optional;

@BotParamCreator(relationTo = UserPreviousStep.class)
public class UserPreviousStepParamCreator implements BotParamCreatorChain {

    private final UserStepService<?> userStepService;

    private final MessageHelper messageHelper;

    public UserPreviousStepParamCreator(
            UserStepService<?> userStepService, MessageHelper messageHelper
    ) {
        this.userStepService = userStepService;
        this.messageHelper = messageHelper;
    }

    @Override
    public Object create(Update message, Parameter parameter) {
        String url = messageHelper.defineUrl(message);
        UserPreviousStepFindStrategy strategy = defineStrategy(parameter);
        Optional<? extends UserStep> userStep = userStepService.findByUrl(url, strategy);
        return userStep
                .map(UserStep::getMessage)
                .orElseThrow();
    }

    private UserPreviousStepFindStrategy defineStrategy(Parameter parameter){
        return Optional.ofNullable(parameter)
                .map(Parameter::getAnnotations)
                .stream()
                .flatMap(Arrays::stream)
                .filter(annotation -> annotation instanceof UserPreviousStep)
                .map(UserPreviousStep.class::cast)
                .map(UserPreviousStep::findStrategy)
                .findFirst()
                .orElseThrow();
    }
}
