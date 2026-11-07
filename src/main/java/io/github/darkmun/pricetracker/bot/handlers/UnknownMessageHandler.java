package io.github.darkmun.pricetracker.bot.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class UnknownMessageHandler implements UpdateHandler {
    @Override
    public void handle(Update update, TelegramClient client) {
        handle(update, client, "Эээ... Вы отправили что-то не то");
    }

    public void handle(Update update, TelegramClient client, String response) {
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
