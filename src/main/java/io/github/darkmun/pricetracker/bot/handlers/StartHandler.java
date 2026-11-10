package io.github.darkmun.pricetracker.bot.handlers;

import io.github.darkmun.pricetracker.bot.MessageManager;
import io.github.darkmun.pricetracker.utils.Resources;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.io.IOException;

@Component("startCommandHandler")
public class StartHandler extends CommandHandler {
    private final MessageManager messageManager;
    private final String messageText;

    public StartHandler(MessageManager messageManager) throws IOException {
        this.messageManager = messageManager;
        this.messageText = Resources.fileToString("bot-messages/start_message.md");
    }

    @Override
    public void handle(Message message) {
        long chatId = message.getChatId();

        messageManager.sendText(chatId, messageText);
    }
}
