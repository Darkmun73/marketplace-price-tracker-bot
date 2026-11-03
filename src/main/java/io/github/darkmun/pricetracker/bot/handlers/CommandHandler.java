package io.github.darkmun.pricetracker.bot.handlers;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public interface CommandHandler {
    void handle(Message message, TelegramClient client);
}
