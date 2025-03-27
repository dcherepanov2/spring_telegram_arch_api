package com.example.spring.telegram.wrapper.db.repository;

import com.example.spring.telegram.wrapper.domain.user.step.RedisUserStep;
import com.example.spring.telegram.wrapper.domain.user.step.UserStep;
import com.example.spring.telegram.wrapper.enumuration.UserPreviousStepFindStrategy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface UserStepRepository <T extends UserStep> extends CrudRepository<T, Long> {

    Optional<List<RedisUserStep>> findByUrl(String url, UserPreviousStepFindStrategy strategy);
}
