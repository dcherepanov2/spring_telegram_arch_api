package com.example.spring.telegram.wrapper.test.clazz;

import com.example.spring.telegram.wrapper.annotations.BotHandler;
import com.example.spring.telegram.wrapper.annotations.BotParam;
import com.example.spring.telegram.wrapper.annotations.BotRequestMapping;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@BotHandler(url = "/test/class/")
public class TestClassBotHandler {

    @BotRequestMapping(url = "/send-message")
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
