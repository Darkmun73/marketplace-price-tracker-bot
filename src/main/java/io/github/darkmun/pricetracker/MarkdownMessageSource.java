package io.github.darkmun.pricetracker;

import jakarta.annotation.PostConstruct;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@Component("messageSource")
public class MarkdownMessageSource extends AbstractMessageSource {
    private Map<Locale, Map<String, MessageFormat>> messagesByLocale;

    private static final Pattern HEADER_PATTERN = Pattern.compile("##\\s+(.+)");

    @PostConstruct
    private void init() throws IOException {
        var messagesByLocale = new ConcurrentHashMap<Locale, Map<String, MessageFormat>>();
        var resolver = new PathMatchingResourcePatternResolver();
        var resources = resolver.getResources("classpath:bot-messages/messages*.md");
        for (var resource : resources) {
            var locale = extractLocale(resource.getFilename());
            var markdownText = resource.getContentAsString(StandardCharsets.UTF_8);
            messagesByLocale.put(locale, parseMarkdown(markdownText, locale));
        }
        this.messagesByLocale = Map.copyOf(messagesByLocale);
    }

    private Map<String, MessageFormat> parseMarkdown(String markdownText, Locale locale) {
        Map<String, MessageFormat> messagesByCode = new ConcurrentHashMap<>();

        var matcher = HEADER_PATTERN.matcher(markdownText);
        int currentIndex = 0;
        while (matcher.find(currentIndex)) {
            String code = matcher.group(1);
            int bodyStart = matcher.end();

            boolean hasNextCode = matcher.find();
            String message;
            if (hasNextCode) {
                currentIndex = matcher.start();
                int bodyEnd = matcher.start();
                message = markdownText.substring(bodyStart, bodyEnd);
            } else {
                currentIndex = markdownText.length() - 1;
                message = markdownText.substring(bodyStart);
            }
            message = message.strip();
            messagesByCode.put(code, new MessageFormat(message, locale));
        }
        return Map.copyOf(messagesByCode);
    }

    private Locale extractLocale(String filename) {
        Objects.requireNonNull(filename, "Resource filename must not be null when extracting locale");
        if (!filename.contains("_")) {
            return Locale.ROOT;
        }
        var languageTag = filename.substring(filename.indexOf('_') + 1, filename.indexOf('.'));

        return Locale.forLanguageTag(languageTag.replace('_', '-'));
    }

    @Override
    protected @Nullable MessageFormat resolveCode(String code, Locale locale) {
        Map<String, MessageFormat> localeMessages = messagesByLocale.get(locale);
        if (localeMessages == null)
            localeMessages = messagesByLocale.get(Locale.ROOT);

        return localeMessages.get(code);
    }
}
