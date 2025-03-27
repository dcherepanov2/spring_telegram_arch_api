package com.example.spring.telegram.wrapper.domain.user.step;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import org.telegram.telegrambots.meta.api.objects.Update;

@Setter
@Getter
@RedisHash("userStep")
public class RedisUserStep extends UserStep {

    @Id
    private Long id;

    private Update message;

    private Long telegramUserId;

    @Indexed
    private String url;
}
