package io.github.darkmun.pricetracker.bot.updates;

import io.github.darkmun.pricetracker.TextProvider;
import io.github.darkmun.pricetracker.bot.CommandHandlersRegistry;
import io.github.darkmun.pricetracker.bot.MessageManager;
import io.github.darkmun.pricetracker.utils.CommandResolver;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
public class MessageUpdateProcessor implements UpdateProcessor {
    private final MessageManager messageManager;
    private final TextProvider textProvider;
    private final CommandHandlersRegistry commandHandlers;

    public MessageUpdateProcessor(MessageManager messageManager, TextProvider textProvider,
                                  CommandHandlersRegistry commandHandlers) {
        this.messageManager = messageManager;
        this.textProvider = textProvider;
        this.commandHandlers = commandHandlers;
    }

    @Override
    public boolean supports(Update update) {
        return update.hasMessage();
    }

    @Override
    public void handle(Update update) {
        Message message = update.getMessage();
        if (!message.hasText()) { send(message, "message.unknown"); return; }

        var commandName = CommandResolver.resolveCommand(message.getText());
        if (commandName == null) { send(message, "message.unknown"); return; }

        var handler = commandHandlers.get(commandName);
        if (handler == null) { send(message, "command.unknown"); return; }
        handler.handle(message);
    }

    private void send(Message message, String key) {
        Long chatId = message.getChatId();
        String languageCode = message.getFrom().getLanguageCode();

        String messageText = textProvider.get(key, languageCode);
        messageManager.sendText(chatId, messageText);
    }
}
