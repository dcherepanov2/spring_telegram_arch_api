package com.example.spring.telegram.wrapper.db.service;

import com.example.spring.telegram.wrapper.db.repository.UserStepRepository;
import com.example.spring.telegram.wrapper.domain.BeforeExecutionContext;
import com.example.spring.telegram.wrapper.domain.user.step.RedisUserStep;
import com.example.spring.telegram.wrapper.enumuration.UserPreviousStepFindStrategy;
import org.jvnet.hk2.annotations.Service;

import java.util.Optional;

@Service
public class DefaultRedisUserStepService implements UserStepService<RedisUserStep> {

    private final UserStepRepository<RedisUserStep> redisUserStepUserStepRepository;

    public DefaultRedisUserStepService(UserStepRepository<RedisUserStep> redisUserStepUserStepRepository) {
        this.redisUserStepUserStepRepository = redisUserStepUserStepRepository;
    }

    @Override
    public Optional<RedisUserStep> findByUrl(String url, UserPreviousStepFindStrategy strategy) {
        return redisUserStepUserStepRepository.findByUrl(url, strategy);
    }

    @Override
    public void createByBeforeExecutionContext(BeforeExecutionContext context) {
        RedisUserStep redisUserStep = createH2UserStep(context);
        redisUserStepUserStepRepository.save(redisUserStep);
    }

    private RedisUserStep createH2UserStep(BeforeExecutionContext context){
        RedisUserStep redisUserStep = new RedisUserStep();
        redisUserStep.setTelegramUserId(context.getUser().getId());
        redisUserStep.setUrl(context.getUrl());
        redisUserStep.setMessage(context.getMessage());
        return redisUserStep;
    }
}
