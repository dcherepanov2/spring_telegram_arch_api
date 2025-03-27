package com.example.spring.telegram.wrapper.db.repository;

import com.example.spring.telegram.wrapper.domain.user.step.RedisUserStep;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisUserStepRepository extends UserStepRepository<RedisUserStep> {
}
