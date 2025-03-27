package com.example.spring.telegram.wrapper.db;

import com.example.spring.telegram.wrapper.db.repository.UserStepFactory;
import com.example.spring.telegram.wrapper.db.service.UserStepService;
import com.example.spring.telegram.wrapper.domain.user.step.H2UserStep;
import com.example.spring.telegram.wrapper.domain.user.step.RedisUserStep;
import com.example.spring.telegram.wrapper.domain.user.step.UserStep;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class DefaultUserStepFactory implements UserStepFactory {

    @Value("${telegram.bot.database}")
    private String databaseType;

    private final UserStepService<H2UserStep> defaultH2UserStepService;

    private final UserStepService<RedisUserStep> defaultRedisUserStepService;

    public DefaultUserStepFactory(
            UserStepService<H2UserStep> defaultH2UserStepService,
            UserStepService<RedisUserStep> defaultRedisUserStepService
    ) {
        this.defaultH2UserStepService = defaultH2UserStepService;
        this.defaultRedisUserStepService = defaultRedisUserStepService;
    }

    @Override
    public UserStepService<?> create() {
        return switch (databaseType) {
            case "h2" -> defaultH2UserStepService;
            case "redis" -> defaultRedisUserStepService;
            default -> throw new IllegalStateException("");
        };
    }
}
