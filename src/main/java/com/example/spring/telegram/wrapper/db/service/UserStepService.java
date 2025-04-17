package com.example.spring.telegram.wrapper.db.service;

import com.example.spring.telegram.wrapper.domain.BeforeExecutionContext;
import com.example.spring.telegram.wrapper.domain.user.step.RedisUserStep;
import com.example.spring.telegram.wrapper.domain.user.step.UserStep;
import com.example.spring.telegram.wrapper.enumuration.UserPreviousStepFindStrategy;

import java.util.List;
import java.util.Optional;

public interface UserStepService<T extends UserStep> {

    Optional<T> findByUrl(String url, UserPreviousStepFindStrategy strategy);

    void createByBeforeExecutionContext(BeforeExecutionContext context);
}
