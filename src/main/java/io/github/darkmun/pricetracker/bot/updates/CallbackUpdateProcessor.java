package io.github.darkmun.pricetracker.bot.updates;

import io.github.darkmun.pricetracker.bot.MessageManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CallbackUpdateProcessor implements UpdateProcessor {
    private final MessageManager messageManager;

    public CallbackUpdateProcessor(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    @Override
    public boolean supports(Update update) {
        return update.hasCallbackQuery();
    }

    @Override
    public void handle(Update update) {
        var callbackQuery = update.getCallbackQuery();
        messageManager.reply(callbackQuery, "inline-button.unknown");
    }
}
