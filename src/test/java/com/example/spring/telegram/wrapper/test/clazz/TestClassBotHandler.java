package com.example.spring.telegram.wrapper.test.clazz;

import com.example.spring.telegram.wrapper.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.spring.telegram.wrapper.enumuration.SenderStrategy.NO_SEND;


@BotHandler(url = "/test/class/")
public class TestClassBotHandler {

    @BotRequestMapping(url = "/send-message", returnStrategy = NO_SEND)
    public void testSendMessage(){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("send-message-without-params");
    }

    @BotRequestMapping(url = "/send-message-with-param")
    public SendMessage testSendMessageWithParam(
            @BotParam(name = "send_message") String testParam,
            @FullTelegramMessage Update message,
            @UserPreviousStep(url = "/other-test-url") Update previousMessage
    ){
        var sendMessage = new SendMessage();
        sendMessage.setText(testParam);
        return sendMessage;
    }


    public static void main(String[] args) {
        String a = "a";
        String a1 = "a";
        System.out.println(a == a1);
    }
}
