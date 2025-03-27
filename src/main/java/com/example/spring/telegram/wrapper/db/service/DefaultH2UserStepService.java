package com.example.spring.telegram.wrapper.db.service;

import com.example.spring.telegram.wrapper.db.repository.UserStepRepository;
import com.example.spring.telegram.wrapper.domain.BeforeExecutionContext;
import com.example.spring.telegram.wrapper.domain.user.step.H2UserStep;
import com.example.spring.telegram.wrapper.domain.user.step.RedisUserStep;
import com.example.spring.telegram.wrapper.enumuration.UserPreviousStepFindStrategy;
import org.jvnet.hk2.annotations.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultH2UserStepService implements UserStepService<H2UserStep> {

    private final UserStepRepository<H2UserStep> userStepRepository;

    public DefaultH2UserStepService(UserStepRepository<H2UserStep> userStepRepository) {
        this.userStepRepository = userStepRepository;
    }

    @Override
    public Optional<List<RedisUserStep>> findByUrl(String url, UserPreviousStepFindStrategy strategy) {
        return userStepRepository.findByUrl(url, strategy);
    }

    @Override
    public void createByBeforeExecutionContext(BeforeExecutionContext context) {
        H2UserStep h2UserStep = createH2UserStep(context);
        userStepRepository.save(h2UserStep);
    }

    private H2UserStep createH2UserStep(BeforeExecutionContext context) {
        H2UserStep h2UserStep = new H2UserStep();
        h2UserStep.setUrl(context.getUrl());
        h2UserStep.setMessage(context.getMessage());
        return h2UserStep;
    }
}
