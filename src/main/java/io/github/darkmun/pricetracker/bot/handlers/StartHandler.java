package io.github.darkmun.pricetracker.bot.handlers;

import io.github.darkmun.pricetracker.utils.Resources;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

@Component("startCommandHandler")
public class StartHandler extends CommandHandler {
    private final TelegramClient client;
    private final String messageText;

    public StartHandler(TelegramClient client) throws IOException {
        this.client = client;
        this.messageText = Resources.fileToString("bot-messages/start_message.md");
    }

    @Override
    public void handle(Message message) {
        var chatId = message.getChatId();

        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(messageText)
                .parseMode("Markdown")
                .build();
        try {
            client.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
