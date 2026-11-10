package io.github.darkmun.pricetracker.bot;

import io.github.darkmun.pricetracker.TextProvider;
import io.github.darkmun.pricetracker.bot.handlers.CommandHandler;
import io.github.darkmun.pricetracker.utils.Commands;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.DefaultLongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.HashMap;
import java.util.Map;

@Component
public class UpdateHandler extends DefaultLongPollingUpdateConsumer {
    private final Map<String, CommandHandler> commandHandlers; // key — command name
    private final MessageManager messageManager;
    private final TextProvider textProvider;

    public UpdateHandler(BotCommandsProperties commandsProperties,
                         Map<String, CommandHandler> commandHandlersByHandlerName,
                         MessageManager messageManager,
                         TextProvider textProvider) {
        this.messageManager = messageManager;
        this.textProvider = textProvider;

        this.commandHandlers = new HashMap<>();
        commandsProperties.commands().forEach((commandName, config) -> {
            this.commandHandlers.put(commandName, commandHandlersByHandlerName.get(config.handler()));
        });
    }

    @Override
    public void consume(Update update) {
        var updateType = UpdateType.getFrom(update);
        switch (updateType) {
            case COMMAND -> {
                var message = update.getMessage();
                handleCommand(message);
            }
            case TEXT, OTHER -> {
                var message = update.getMessage();
                handleOtherMessage(message);
            }
            case INLINE_BUTTON -> {
                var callbackQuery = update.getCallbackQuery();
                handleInlineButton(callbackQuery);
            }
        }
    }

    private void handleCommand(Message message) {
        String command = Commands.getFrom(message.getText());
        var handler = commandHandlers.get(command);
        if (handler != null) {
            handler.handle(message);
        }
        else {
            long chatId = message.getChatId();
            String languageCode = message.getFrom().getLanguageCode();
            String messageText = textProvider.get("command.unknown", languageCode);

            messageManager.sendText(chatId, messageText);
        }
    }

    private void handleOtherMessage(Message message) {
        long chatId = message.getChatId();
        String languageCode = message.getFrom().getLanguageCode();
        String messageText = textProvider.get("message.unknown", languageCode);

        messageManager.sendText(chatId, messageText);
    }

    private void handleInlineButton(CallbackQuery callbackQuery) {
        long chatId = callbackQuery.getMessage().getChatId();
        String languageCode = callbackQuery.getFrom().getLanguageCode();
        String messageText = textProvider.get("inline-button.unknown", languageCode);

        messageManager.sendText(chatId, messageText);
    }
}
