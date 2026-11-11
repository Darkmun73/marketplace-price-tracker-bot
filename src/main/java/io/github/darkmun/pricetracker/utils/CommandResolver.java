package io.github.darkmun.pricetracker.utils;

import java.util.regex.Pattern;

/**
 * Works with telegram bot commands
 */
public class CommandResolver {

    private static final Pattern commandPattern = Pattern.compile("^/\\w+$");

    public static boolean isCommand(String messageText) {
        return commandPattern.matcher(messageText).find();
    }

    public static String getFrom(String messageText) {
        var matcher = commandPattern.matcher(messageText);
        if (matcher.find()) {
            String commandWithSlash = matcher.group();
            return commandWithSlash.substring(1);
        } else {
            return null;
        }
    }
}
