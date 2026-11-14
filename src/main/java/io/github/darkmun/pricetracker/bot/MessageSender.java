package io.github.darkmun.pricetracker.bot;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface MessageSender {
    void sendText(long chatId, String text);

    /**
     * @param message message to reply
     * @param key key referring to text from messages.md
     */
    void reply(Message message, String key);

    /**
     * @param callbackQuery callback query to reply
     * @param key key referring to text from messages.md
     */
    void reply(CallbackQuery callbackQuery, String key);
}
