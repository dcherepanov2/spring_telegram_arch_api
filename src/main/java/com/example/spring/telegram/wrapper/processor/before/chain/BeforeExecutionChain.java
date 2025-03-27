package com.example.spring.telegram.wrapper.processor.before.chain;

import com.example.spring.telegram.wrapper.domain.BeforeExecutionContext;

public interface BeforeExecutionChain {

    void execute(BeforeExecutionContext beforeExecutionContext);
}
