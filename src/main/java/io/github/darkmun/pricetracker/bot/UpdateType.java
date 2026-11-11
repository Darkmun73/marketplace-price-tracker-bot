package io.github.darkmun.pricetracker.bot;

import io.github.darkmun.pricetracker.utils.CommandResolver;
import org.telegram.telegrambots.meta.api.objects.Update;

public enum UpdateType {
    TEXT,
    COMMAND,
    INLINE_BUTTON,
    OTHER;

    public static UpdateType getFrom(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            if (CommandResolver.isCommand(messageText)) {
                return COMMAND;
            } else {
                return TEXT;
            }
        } else if (update.hasCallbackQuery()) {
            return INLINE_BUTTON;
        } else {
            return OTHER;
        }
    }
}
