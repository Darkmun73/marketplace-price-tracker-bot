package io.github.darkmun.pricetracker.bot.handlers;

import io.github.darkmun.pricetracker.TextProvider;
import io.github.darkmun.pricetracker.bot.MessageManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.io.IOException;

@Component("startCommandHandler")
public class StartHandler extends CommandHandler {
    private final MessageManager messageManager;

    public StartHandler(MessageManager messageManager) throws IOException {
        this.messageManager = messageManager;
    }

    @Override
    public void handle(Message message) {
        messageManager.reply(message, "command.start");
    }
}
