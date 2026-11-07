package io.github.darkmun.pricetracker.bot;

import io.github.darkmun.pricetracker.bot.handlers.CommandHandler;
import io.github.darkmun.pricetracker.utils.Commands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.DefaultLongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class PriceTrackerTelegramBot extends DefaultLongPollingUpdateConsumer implements SpringLongPollingBot {
    private final String botToken;
    private final TelegramClient client;

    private final Map<String, CommandHandler> commandHandlers; // key — command name

    public PriceTrackerTelegramBot(@Value("${bot.token}") String botToken,
                                   BotCommandsProperties commandsProperties,
                                   Map<String, CommandHandler> commandHandlersByHandlerName) {
        this.botToken = botToken;
        client = new OkHttpTelegramClient(botToken);

        this.commandHandlers = new HashMap<>();
        commandsProperties.commands().forEach((commandName, config) -> {
            this.commandHandlers.put(commandName, commandHandlersByHandlerName.get(config.handler()));
        });
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        UpdateType updateType = UpdateType.getFrom(update);
        if (updateType == UpdateType.COMMAND) {
            var command = Commands.getFrom(update.getMessage().getText());
            var handler = commandHandlers.get(command);
            if (handler != null)
                handler.handle(update, client);
            else
                handleUnknownMessage(update.getMessage().getChatId());
        }
    }

    private void handleUnknownMessage(Long chatId) {
        SendMessage message = SendMessage
                .builder()
                .chatId(chatId)
                .text("Эээ... Вы написали что-то не то")
                .build();
        try {
            client.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
