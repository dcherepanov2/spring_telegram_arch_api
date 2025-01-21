package com.example.spring.telegram.wrapper.test.clazz;

import com.example.spring.telegram.wrapper.annotation.BotHandler;
import com.example.spring.telegram.wrapper.annotation.BotParam;
import com.example.spring.telegram.wrapper.annotation.BotRequestMapping;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.spring.telegram.wrapper.strategy.SenderStrategy.NO_SEND;


@BotHandler(url = "/test/class/")
public class TestClassBotHandler {

    @BotRequestMapping(url = "/send-message", returnStrategy = NO_SEND)
    public SendMessage testSendMessage(){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("send-message-without-params");
        return new SendMessage();
    }

    @BotRequestMapping(url = "/send-message-with-param")
    public SendMessage testSendMessageWithParam(@BotParam(name = "send_message") String testParam){
        var sendMessage = new SendMessage();
        sendMessage.setText(testParam);
        return sendMessage;
    }
}
