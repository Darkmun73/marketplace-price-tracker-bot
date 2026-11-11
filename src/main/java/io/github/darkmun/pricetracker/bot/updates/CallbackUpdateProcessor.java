package io.github.darkmun.pricetracker.bot.updates;

import io.github.darkmun.pricetracker.TextProvider;
import io.github.darkmun.pricetracker.bot.MessageManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CallbackUpdateProcessor implements UpdateProcessor {
    private final MessageManager messageManager;
    private final TextProvider textProvider;

    public CallbackUpdateProcessor(MessageManager messageManager, TextProvider textProvider) {
        this.messageManager = messageManager;
        this.textProvider = textProvider;
    }

    @Override
    public boolean supports(Update update) {
        return update.hasCallbackQuery();
    }

    @Override
    public void handle(Update update) {
        var callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();
        String languageCode = callbackQuery.getFrom().getLanguageCode();

        String messageText = textProvider.get("inline-button.unknown", languageCode);
        messageManager.sendText(chatId, messageText);
    }
}
