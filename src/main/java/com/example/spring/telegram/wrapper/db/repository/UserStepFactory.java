package com.example.spring.telegram.wrapper.db.repository;

import com.example.spring.telegram.wrapper.db.service.UserStepService;
import com.example.spring.telegram.wrapper.domain.user.step.UserStep;

public interface UserStepFactory {

    UserStepService<? extends UserStep> create();
}
