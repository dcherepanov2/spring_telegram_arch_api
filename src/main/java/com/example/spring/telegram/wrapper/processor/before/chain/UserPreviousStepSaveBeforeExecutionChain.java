package com.example.spring.telegram.wrapper.processor.before.chain;

import com.example.spring.telegram.wrapper.domain.BeforeExecutionContext;
import com.example.spring.telegram.wrapper.domain.user.step.RedisUserStep;
import com.example.spring.telegram.wrapper.helper.MessageHelper;
import com.example.spring.telegram.wrapper.db.service.UserStepService;
import org.jvnet.hk2.annotations.Service;

@Service
public class UserPreviousStepSaveBeforeExecutionChain implements BeforeExecutionChain {

    private final UserStepService<?> userStepService;

    public UserPreviousStepSaveBeforeExecutionChain(UserStepService<?> userStepService) {
        this.userStepService = userStepService;
    }

    @Override
    public void execute(BeforeExecutionContext context) {
        userStepService.createByBeforeExecutionContext(context);
    }
}
