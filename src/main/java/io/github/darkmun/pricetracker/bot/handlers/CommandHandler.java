package io.github.darkmun.pricetracker.bot.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public abstract class CommandHandler {
    public abstract void handle(Update update, TelegramClient client);
}
