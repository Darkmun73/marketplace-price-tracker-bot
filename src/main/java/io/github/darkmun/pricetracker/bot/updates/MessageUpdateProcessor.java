package io.github.darkmun.pricetracker.bot.updates;

import io.github.darkmun.pricetracker.bot.CommandHandlersRegistry;
import io.github.darkmun.pricetracker.bot.MessageManager;
import io.github.darkmun.pricetracker.utils.CommandResolver;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
public class MessageUpdateProcessor implements UpdateProcessor {
    private final MessageManager messageManager;
    private final CommandHandlersRegistry commandHandlers;

    public MessageUpdateProcessor(MessageManager messageManager,
                                  CommandHandlersRegistry commandHandlers) {
        this.messageManager = messageManager;
        this.commandHandlers = commandHandlers;
    }

    @Override
    public boolean supports(Update update) {
        return update.hasMessage();
    }

    @Override
    public void handle(Update update) {
        Message message = update.getMessage();
        if (!message.hasText()) { messageManager.reply(message, "message.unknown"); return; }

        var commandName = CommandResolver.resolveCommand(message.getText());
        if (commandName == null) { messageManager.reply(message, "message.unknown"); return; }

        var handler = commandHandlers.get(commandName);
        if (handler == null) { messageManager.reply(message, "command.unknown"); return; }
        handler.handle(message);
    }
}
