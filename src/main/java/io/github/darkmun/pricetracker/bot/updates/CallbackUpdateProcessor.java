package io.github.darkmun.pricetracker.bot.updates;

import io.github.darkmun.pricetracker.bot.MessageSender;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CallbackUpdateProcessor implements UpdateProcessor {
    private final MessageSender messageSender;

    public CallbackUpdateProcessor(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public boolean supports(Update update) {
        return update.hasCallbackQuery();
    }

    @Override
    public void handle(Update update) {
        var callbackQuery = update.getCallbackQuery();
        messageSender.reply(callbackQuery, "inline-button.unknown");
    }
}
