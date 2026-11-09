package io.github.darkmun.pricetracker.bot;

import io.github.darkmun.pricetracker.bot.handlers.CommandHandler;
import io.github.darkmun.pricetracker.bot.handlers.UnknownMessageHandler;
import io.github.darkmun.pricetracker.utils.Commands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.DefaultLongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
public class PriceTrackerBot extends DefaultLongPollingUpdateConsumer implements SpringLongPollingBot {
    private final String botToken;

    private final Map<String, CommandHandler> commandHandlers; // key — command name
    private final UnknownMessageHandler unknownMessageHandler;

    public PriceTrackerBot(@Value("${bot.token}") String botToken,
                           BotCommandsProperties commandsProperties,
                           Map<String, CommandHandler> commandHandlersByHandlerName,
                           UnknownMessageHandler unknownMessageHandler) {
        this.botToken = botToken;
        this.unknownMessageHandler = unknownMessageHandler;

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
                handler.handle(update);
            else
                unknownMessageHandler.handle(
                        update,
                        "Такой команды нет\uD83D\uDE2F Может вы где-нибудь опечатались?"
                );
        } else {
            unknownMessageHandler.handle(update);
        }
    }
}
