package io.github.darkmun.pricetracker.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Works with resources directory
 */
public class Resources {

    public static String fileToString(String fileName) throws IOException {
        Resource resource = new ClassPathResource(fileName);
        return resource.getContentAsString(StandardCharsets.UTF_8);
    }

}
