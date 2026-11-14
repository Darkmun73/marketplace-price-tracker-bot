package io.github.darkmun.pricetracker.bot;

import io.github.darkmun.pricetracker.bot.handlers.CommandHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandHandlersRegistry {
    private final Map<String, CommandHandler> handlers; // key — command name

    public CommandHandlersRegistry(
            BotProperties properties,
            Map<String, CommandHandler> handlersByBeanName
    ) {
        var result = new HashMap<String, CommandHandler>();

        properties.commands().forEach((name, config) -> {
            var handler = handlersByBeanName.get(config.handler());
            if (handler == null) {
                throw new IllegalStateException(
                        "There is no handler bean '%s' for command '%s'"
                                .formatted(config.handler(), name)
                );
            }
            result.put(name, handler);
        });

        this.handlers = Map.copyOf(result);
    }

    public CommandHandler get(String commandName) {
        return handlers.get(commandName);
    }
}
