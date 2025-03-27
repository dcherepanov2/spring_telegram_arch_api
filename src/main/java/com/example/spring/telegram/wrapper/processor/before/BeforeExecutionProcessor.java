package com.example.spring.telegram.wrapper.processor.before;

import com.example.spring.telegram.wrapper.domain.BotHandlerContext;

public interface BeforeExecutionProcessor {

    void process(BotHandlerContext context);
}
