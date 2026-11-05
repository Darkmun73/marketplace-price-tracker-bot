package io.github.darkmun.pricetracker.bot.handlers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class CommandHandler implements UpdateHandler {
    private final String name;
}
