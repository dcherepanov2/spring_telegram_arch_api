package com.example.spring.telegram.wrapper.runner;

import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

class BotMessageHandler extends TelegramLongPollingBot {
    private final BotInvoker botInvoker;
    private final TelegramBotsApi telegramBotsApi;

    @Value("${bot.token}")
    private String botToken;

    public BotMessageHandler(BotInvoker botInvoker) throws TelegramApiException {
        this.botInvoker = botInvoker;
        telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
    }

    @PostConstruct
    private void init() throws TelegramApiException {
        telegramBotsApi.registerBot(this);
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        BotApiMethod<?> sendObject = botInvoker.invoke(update);
        execute(sendObject);
    }


    public void sendChatMessage(BotApiMethod<?> sendObject) {
        try {
            if(sendObject instanceof SendMessage sendMessage){
                execute(sendMessage);
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }// TODO: лютая дерьмина, переписать на стретегию или цепочку обязанностей

    @Override
    public String getBotUsername() {
        return "bot";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
