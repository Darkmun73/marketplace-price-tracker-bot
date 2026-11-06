package io.github.darkmun.pricetracker.bot;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "bot")
public record BotCommandsProperties(Map<String, CommandConfig> commands) {
    public record CommandConfig(String handler) {}
}
