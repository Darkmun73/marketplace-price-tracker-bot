package io.github.darkmun.pricetracker.bot.handlers;

import io.github.darkmun.pricetracker.TextProvider;
import io.github.darkmun.pricetracker.bot.MessageManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.io.IOException;

@Component("startCommandHandler")
public class StartHandler extends CommandHandler {
    private final MessageManager messageManager;
    private final TextProvider textProvider;

    public StartHandler(MessageManager messageManager,
                        TextProvider textProvider) throws IOException {
        this.messageManager = messageManager;
        this.textProvider = textProvider;
    }

    @Override
    public void handle(Message message) {
        long chatId = message.getChatId();
        String languageCode = message.getFrom().getLanguageCode();

        String messageText = textProvider.get("command.start", languageCode);
        messageManager.sendText(chatId, messageText);
    }
}
