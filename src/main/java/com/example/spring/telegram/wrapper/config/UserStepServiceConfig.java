package com.example.spring.telegram.wrapper.config;

import com.example.spring.telegram.wrapper.db.repository.H2UserStepRepository;
import com.example.spring.telegram.wrapper.db.repository.RedisUserStepRepository;
import com.example.spring.telegram.wrapper.db.service.DefaultH2UserStepService;
import com.example.spring.telegram.wrapper.db.service.DefaultRedisUserStepService;
import com.example.spring.telegram.wrapper.db.service.UserStepService;
import com.example.spring.telegram.wrapper.domain.user.step.H2UserStep;
import com.example.spring.telegram.wrapper.domain.user.step.RedisUserStep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserStepServiceConfig {

    @Bean
    public UserStepService<H2UserStep> h2UserStepUserStepService(H2UserStepRepository h2UserStepRepository){
        return new DefaultH2UserStepService(h2UserStepRepository);
    }

    @Bean
    public UserStepService<RedisUserStep> redisUserStepUserStepService(RedisUserStepRepository redisUserStepRepository){
        return new DefaultRedisUserStepService(redisUserStepRepository);
    }
}
