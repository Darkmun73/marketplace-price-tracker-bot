package io.github.darkmun.pricetracker.bot;

import io.github.darkmun.pricetracker.bot.updates.UpdateProcessorRegistry;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.DefaultLongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UpdateHandler extends DefaultLongPollingUpdateConsumer {
    private final UpdateProcessorRegistry processors;

    public UpdateHandler(UpdateProcessorRegistry processors) {
        this.processors = processors;
    }

    @Override
    public void consume(Update update) {
        processors.process(update);
    }
}
