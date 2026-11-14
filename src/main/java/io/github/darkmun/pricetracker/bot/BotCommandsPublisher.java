package io.github.darkmun.pricetracker.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.commands.DeleteMyCommands;
import org.telegram.telegrambots.meta.api.methods.commands.GetMyCommands;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BotCommandsPublisher implements ApplicationRunner {
    private final TelegramClient telegramClient;
    private final BotProperties botProperties;

    @Override
    public void run(ApplicationArguments args) {
        var botCommands = getCommandsFromProperties();
        try {
            telegramClient.execute(new DeleteMyCommands());
            if (!botCommands.isEmpty()) {
                var setCommandsMethod = new SetMyCommands(botCommands);
                telegramClient.execute(setCommandsMethod);
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private List<BotCommand> getCommandsFromProperties() {
        var botCommands = new ArrayList<BotCommand>();
        botProperties.commands()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().visible())
                .forEach(entry -> {
                    var botCommand = new BotCommand(entry.getKey(), entry.getValue().description());
                    botCommands.add(botCommand);
                });
        return botCommands;
    }
}
