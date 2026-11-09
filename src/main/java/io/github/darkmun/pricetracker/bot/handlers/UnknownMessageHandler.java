package io.github.darkmun.pricetracker.bot.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
@RequiredArgsConstructor
public class UnknownMessageHandler  {
    private final TelegramClient client;

    public void handle(Update update) {
        handle(update, "Эээ... Вы отправили что-то не то");
    }

    public void handle(Update update, String response) {
        SendMessage message = SendMessage
                .builder()
                .chatId(update.getMessage().getChatId())
                .text(response)
                .build();
        try {
            client.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
