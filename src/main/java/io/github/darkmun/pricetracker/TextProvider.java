package io.github.darkmun.pricetracker;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class TextProvider {
    private final MessageSource messageSource;

    public String get(String code, String languageTag) {
        return messageSource.getMessage(
                code,
                null,
                Locale.forLanguageTag(languageTag)
        );
    }
}
