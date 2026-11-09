package io.github.darkmun.pricetracker.bot.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
@RequiredArgsConstructor
public class UnknownMessageHandler  {
    private final TelegramClient client;

    public void handle(Message message) {
        handle(message, "Эээ... Вы отправили что-то не то");
    }

    public void handle(Message message, String response) {
        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(message.getChatId())
                .text(response)
                .build();
        try {
            client.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
