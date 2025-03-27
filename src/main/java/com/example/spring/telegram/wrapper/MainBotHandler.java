package com.example.spring.telegram.wrapper;

import com.example.spring.telegram.wrapper.runner.BotInvoker;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Service
public class MainBotHandler extends TelegramLongPollingBot {

    private final BotInvoker botInvoker;
    private final TelegramBotsApi telegramBotsApi;

    @Value("${telegram.bot.token}")
    private String botToken;

    public MainBotHandler(BotInvoker botInvoker) throws TelegramApiException {
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

    @Override
    public String getBotUsername() {
        return "bot";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
