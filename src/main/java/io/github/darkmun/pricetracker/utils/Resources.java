package io.github.darkmun.pricetracker.utils;

import org.springframework.core.io.ClassPathResource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Works with resources directory
 */
public class Resources {

    public static String fileToString(String fileName) throws IOException {
        var resource = new ClassPathResource(fileName);
        return resource.getContentAsString(StandardCharsets.UTF_8);
    }

}
