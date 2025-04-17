package com.example.spring.telegram.wrapper.execution.before.chain;

import com.example.spring.telegram.wrapper.domain.BeforeExecutionContext;

public interface BeforeExecutionChain {

    void execute(BeforeExecutionContext beforeExecutionContext);
}
