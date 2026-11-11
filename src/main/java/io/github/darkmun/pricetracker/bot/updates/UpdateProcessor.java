package io.github.darkmun.pricetracker.bot.updates;

import org.telegram.telegrambots.meta.api.objects.Update;

/** Handles one kind of Telegram update. */
public interface UpdateProcessor {
    boolean supports(Update update);

    void handle(Update update);
}
