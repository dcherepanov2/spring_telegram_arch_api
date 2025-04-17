package com.example.spring.telegram.wrapper.execution.before;

import com.example.spring.telegram.wrapper.domain.BeforeExecutionContext;
import com.example.spring.telegram.wrapper.domain.BotHandlerContext;
import com.example.spring.telegram.wrapper.helper.MessageHelper;
import com.example.spring.telegram.wrapper.execution.before.chain.BeforeExecutionChain;
import org.jvnet.hk2.annotations.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class DefaultBeforeExecutionProcessor implements BeforeExecutionProcessor {

    private final List<BeforeExecutionChain> beforeExecutionChains;

    private final MessageHelper messageHelper;

    public DefaultBeforeExecutionProcessor(
            List<BeforeExecutionChain> beforeExecutionChains,
            MessageHelper messageHelper
    ) {
        this.beforeExecutionChains = beforeExecutionChains;
        this.messageHelper = messageHelper;
    }

    @Override
    public void process(BotHandlerContext botHandlerContext) {
        BeforeExecutionContext beforeExecutionContext = buildContext(botHandlerContext);
        beforeExecutionChains
                .forEach(beforeExecutionChain -> beforeExecutionChain.execute(beforeExecutionContext));
    }

    private BeforeExecutionContext buildContext(BotHandlerContext botHandlerContext){
        Update message = botHandlerContext.getMessage();
        return BeforeExecutionContext.builder()
                .url(messageHelper.defineUrl(message))
                .user(messageHelper.getUser(message))
                .message(message)
                .build();
    }
}
