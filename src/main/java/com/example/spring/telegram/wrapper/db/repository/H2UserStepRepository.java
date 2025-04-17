package com.example.spring.telegram.wrapper.db.repository;

import com.example.spring.telegram.wrapper.domain.user.step.H2UserStep;
import org.springframework.stereotype.Repository;

@Repository
public interface H2UserStepRepository extends UserStepRepository<H2UserStep> {
}
