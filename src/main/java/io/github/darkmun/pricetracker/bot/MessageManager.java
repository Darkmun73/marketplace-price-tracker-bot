package io.github.darkmun.pricetracker.bot;

import io.github.darkmun.pricetracker.TextProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
@RequiredArgsConstructor
public class MessageManager implements MessageSender {
    private final TelegramClient client;
    private final TextProvider textProvider;

    public void sendText(long chatId, String text) {
        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .parseMode("Markdown")
                .text(text)
                .build();
        try {
            client.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void reply(Message message, String key) {
        long chatId = message.getChatId();
        String languageCode = message.getFrom().getLanguageCode();

        String messageText = textProvider.get(key, languageCode);
        sendText(chatId, messageText);
    }

    public void reply(CallbackQuery callbackQuery, String key) {
        long chatId = callbackQuery.getMessage().getChatId();
        String languageCode = callbackQuery.getFrom().getLanguageCode();

        String messageText = textProvider.get(key, languageCode);
        sendText(chatId, messageText);
    }
}
