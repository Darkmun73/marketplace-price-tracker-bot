package io.github.darkmun.pricetracker.bot.handlers;

import org.telegram.telegrambots.meta.api.objects.message.Message;

public abstract class CommandHandler {
    public abstract void handle(Message message);
}
