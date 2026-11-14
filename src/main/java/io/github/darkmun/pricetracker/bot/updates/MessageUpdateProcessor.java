package io.github.darkmun.pricetracker.bot.updates;

import io.github.darkmun.pricetracker.bot.CommandHandlersRegistry;
import io.github.darkmun.pricetracker.bot.MessageSender;
import io.github.darkmun.pricetracker.utils.CommandResolver;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
public class MessageUpdateProcessor implements UpdateProcessor {
    private final MessageSender messageSender;
    private final CommandHandlersRegistry commandHandlers;

    public MessageUpdateProcessor(MessageSender messageSender,
                                  CommandHandlersRegistry commandHandlers) {
        this.messageSender = messageSender;
        this.commandHandlers = commandHandlers;
    }

    @Override
    public boolean supports(Update update) {
        return update.hasMessage();
    }

    @Override
    public void handle(Update update) {
        Message message = update.getMessage();
        if (!message.hasText()) { messageSender.reply(message, "message.unknown"); return; }

        var commandName = CommandResolver.resolveCommand(message.getText());
        if (commandName == null) { messageSender.reply(message, "message.unknown"); return; }

        var handler = commandHandlers.get(commandName);
        if (handler == null) { messageSender.reply(message, "command.unknown"); return; }
        handler.handle(message);
    }
}
