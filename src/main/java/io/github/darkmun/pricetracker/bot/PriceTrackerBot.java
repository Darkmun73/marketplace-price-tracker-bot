package io.github.darkmun.pricetracker.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

@Component
public class PriceTrackerBot implements SpringLongPollingBot {
    private final String botToken;
    private final UpdateHandler updateHandler;

    public PriceTrackerBot(BotProperties botProperties, UpdateHandler updateHandler) {
        this.botToken = botProperties.token();
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
