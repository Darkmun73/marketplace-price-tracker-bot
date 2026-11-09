package io.github.darkmun.pricetracker.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

@Component
public class PriceTrackerBot implements SpringLongPollingBot {
    private final String botToken;
    private final UpdateHandler updateHandler;

    public PriceTrackerBot(@Value("${bot.token}") String botToken, UpdateHandler updateHandler) {
        this.botToken = botToken;
        this.updateHandler = updateHandler;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return updateHandler;
    }
}
