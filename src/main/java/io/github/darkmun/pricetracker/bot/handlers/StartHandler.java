package io.github.darkmun.pricetracker.bot.handlers;

import io.github.darkmun.pricetracker.utils.Resources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

@Component
public class StartHandler extends CommandHandler {
    String messageText = Resources.fileToString("bot-messages/start_message.md");

    public StartHandler(@Value("${bot.commands.start.name}") String name) throws IOException {
        super(name);
    }

    @Override
    public void handle(Update update, TelegramClient client) {
        var chatId = update.getMessage().getChatId();

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
