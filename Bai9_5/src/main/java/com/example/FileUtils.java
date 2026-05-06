package com.example;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FileUtils – cross-platform file path utilities.
 */
public class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    /**
     * Builds a cross-platform path from a variable number of segments.
     *
     * @param first the root segment; must not be null or blank
     * @param more  additional path segments
     * @return the composed {@link Path}
     * @throws IllegalArgumentException if {@code first} is null or blank
     */
    public Path buildPath(String first, String... more) {
        if (first == null || first.isBlank()) {
            LOGGER.error("buildPath() called with null/blank root segment");
            throw new IllegalArgumentException("Root path segment must not be null or blank");
        }
        Path result = Paths.get(first, more);
        LOGGER.debug("buildPath() -> {}", result);
        return result;
    }

    /**
     * Returns the file extension from a path, or an empty string if none.
     *
     * @param path the path to inspect; must not be null
     * @return lowercase extension without the leading dot, or {@code ""}
     * @throws IllegalArgumentException if {@code path} is null
     */
    public String getExtension(Path path) {
        if (path == null) {
            throw new IllegalArgumentException("Path must not be null");
        }
        String fileName = path.getFileName().toString();
        int dot = fileName.lastIndexOf('.');
        String ext = (dot > 0) ? fileName.substring(dot + 1).toLowerCase() : "";
        LOGGER.debug("getExtension({}) = '{}'", path, ext);
        return ext;
    }

    /**
     * Returns the OS-specific path separator character.
     *
     * @return the separator as a single-character string
     */
    public String getOsSeparator() {
        String sep = java.io.File.separator;
        LOGGER.info("OS path separator: '{}'", sep);
        return sep;
    }
}
