package io.github.darkmun.pricetracker.bot;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "bot")
public record BotProperties(
        String token,
        Map<String, CommandConfig> commands
) {
    public record CommandConfig(String description, boolean visible, String handler) {}
}
