package io.github.darkmun.pricetracker.bot;

import org.telegram.telegrambots.meta.api.objects.Update;

public enum UpdateType {
    TEXT,
    COMMAND,
    INLINE_BUTTON,
    OTHER;

    public static UpdateType getFrom(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var messageText = update.getMessage().getText();
            if (messageText.startsWith("/"))
                return COMMAND;
            else
                return TEXT;
        } else if (update.hasCallbackQuery()) {
            return INLINE_BUTTON;
        } else {
            return OTHER;
        }
    }
}
