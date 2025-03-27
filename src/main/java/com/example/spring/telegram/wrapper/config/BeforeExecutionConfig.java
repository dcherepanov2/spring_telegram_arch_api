package com.example.spring.telegram.wrapper.config;

import com.example.spring.telegram.wrapper.db.repository.UserStepFactory;
import com.example.spring.telegram.wrapper.db.service.UserStepService;
import com.example.spring.telegram.wrapper.domain.user.step.UserStep;
import com.example.spring.telegram.wrapper.helper.MessageHelper;
import com.example.spring.telegram.wrapper.processor.before.BeforeExecutionProcessor;
import com.example.spring.telegram.wrapper.processor.before.DefaultBeforeExecutionProcessor;
import com.example.spring.telegram.wrapper.processor.before.chain.BeforeExecutionChain;
import com.example.spring.telegram.wrapper.processor.before.chain.UserPreviousStepSaveBeforeExecutionChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BeforeExecutionConfig {

    private final UserStepService<? extends UserStep> userStepService;
    private final MessageHelper messageHelper;

    public BeforeExecutionConfig(UserStepFactory userStepFactory, MessageHelper messageHelper) {
        this.userStepService = userStepFactory.create();
        this.messageHelper = messageHelper;
    }

    @Bean
    public BeforeExecutionProcessor beforeExecutionProcessor(){
        return new DefaultBeforeExecutionProcessor(
                beforeExecutionChains(),
                messageHelper
        );
    }

    private BeforeExecutionChain userPreviousStepSaveBeforeExecutionChain(){
        return new UserPreviousStepSaveBeforeExecutionChain(userStepService);
    }

    private List<BeforeExecutionChain> beforeExecutionChains(){
        List<BeforeExecutionChain> chains = new ArrayList<>();
        chains.add(userPreviousStepSaveBeforeExecutionChain());
        return chains;
    }
}
