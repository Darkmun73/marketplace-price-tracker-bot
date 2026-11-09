package io.github.darkmun.pricetracker.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
@RequiredArgsConstructor
public class MessageManager {
    private final TelegramClient client;

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
}
