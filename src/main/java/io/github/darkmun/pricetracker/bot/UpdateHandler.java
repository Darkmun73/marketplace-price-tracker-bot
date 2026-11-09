package io.github.darkmun.pricetracker.bot;

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

    public UpdateHandler(BotCommandsProperties commandsProperties,
                           Map<String, CommandHandler> commandHandlersByHandlerName,
                           MessageManager messageManager) {
        this.messageManager = messageManager;

        this.commandHandlers = new HashMap<>();
        commandsProperties.commands().forEach((commandName, config) -> {
            this.commandHandlers.put(commandName, commandHandlersByHandlerName.get(config.handler()));
        });
    }

    @Override
    public void consume(Update update) {
        UpdateType updateType = UpdateType.getFrom(update);
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
        var command = Commands.getFrom(message.getText());
        var handler = commandHandlers.get(command);
        if (handler != null)
            handler.handle(message);
        else
            messageManager.sendText(
                    message.getChatId(),
                    "Такой команды нет\uD83D\uDE2F Может вы где-нибудь опечатались?"
            );
    }

    private void handleOtherMessage(Message message) {
        messageManager.sendText(
                message.getChatId(),
                "Эээ... Вы отправили что-то не то"
        );
    }

    private void handleInlineButton(CallbackQuery callbackQuery) {
        messageManager.sendText(
                callbackQuery.getMessage().getChatId(),
                "Эта кнопка пока ничего не делает"
        );
    }
}
