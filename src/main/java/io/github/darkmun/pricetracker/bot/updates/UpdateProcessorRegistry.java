package io.github.darkmun.pricetracker.bot.updates;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class UpdateProcessorRegistry {
    private final List<UpdateProcessor> processors;

    public UpdateProcessorRegistry(List<UpdateProcessor> processors) {
        this.processors = List.copyOf(processors);
    }

    public void process(Update update) {
        processors.stream()
                .filter(processor -> processor.supports(update))
                .findFirst()
                .ifPresent(processor -> processor.handle(update));
    }
}
