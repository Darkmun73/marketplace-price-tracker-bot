package io.github.darkmun.pricetracker.bot.handlers;

import io.github.darkmun.pricetracker.bot.MessageSender;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component("startCommandHandler")
public class StartHandler extends CommandHandler {
    private final MessageSender messageSender;

    public StartHandler(MessageSender messageSender){
        this.messageSender = messageSender;
    }

    @Override
    public void handle(Message message) {
        messageSender.reply(message, "command.start");
    }
}
