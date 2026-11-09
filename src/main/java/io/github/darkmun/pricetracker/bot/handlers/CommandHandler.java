package io.github.darkmun.pricetracker.bot.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class CommandHandler {
    public abstract void handle(Update update);
}
