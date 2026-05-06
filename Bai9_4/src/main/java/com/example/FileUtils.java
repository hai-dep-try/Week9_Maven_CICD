package com.example;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FileUtils – cross-platform file path utilities.
 *
 * <p>All path construction uses {@link java.nio.file.Path} (NIO.2 API)
 * so paths are correct on Windows, Linux, and macOS without
 * hardcoding {@code \} or {@code /} separators.</p>
 *
 * <h2>The "It Works On My Machine" Problem</h2>
 * <p>Hardcoded path separators cause test failures across OS:</p>
 * <pre>
 *   // BROKEN on Linux/macOS – uses Windows backslash:
 *   String path = "data\\config\\app.properties";
 *
 *   // BROKEN on Windows – uses Unix forward-slash (works accidentally
 *   // on Windows too, but not the canonical form):
 *   String path = "data/config/app.properties";
 *
 *   // CORRECT – let the JVM choose the separator for the current OS:
 *   Path path = Paths.get("data", "config", "app.properties");
 * </pre>
 */
public class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    /**
     * Builds a cross-platform path from a variable number of path segments.
     *
     * <p>Uses {@link Paths#get(String, String...)} which resolves path
     * separators automatically for the current operating system.</p>
     *
     * @param first the root segment (must not be null or empty)
     * @param more  additional path segments (may be empty)
     * @return a {@link Path} composed of all segments
     * @throws IllegalArgumentException if {@code first} is null or blank
     */
    public Path buildPath(String first, String... more) {
        if (first == null || first.isBlank()) {
            LOGGER.error("buildPath() called with null/blank root segment");
            throw new IllegalArgumentException("Root path segment must not be null or blank");
        }
        Path result = Paths.get(first, more);
        LOGGER.debug("buildPath() -> {} (OS separator: '{}')", result, java.io.File.separator);
        return result;
    }

    /**
     * Returns the file extension from a path, or an empty string if none.
     *
     * <p>Uses {@link Path#getFileName()} to extract just the filename
     * component, avoiding OS-specific string parsing.</p>
     *
     * @param path the path to inspect (must not be null)
     * @return lowercase extension without leading dot, or {@code ""} if absent
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
     * <p>Delegates to {@link java.io.File#separator} – this is the character
     * the current OS uses between path segments:</p>
     * <ul>
     *   <li>Windows: {@code \}</li>
     *   <li>Linux / macOS: {@code /}</li>
     * </ul>
     *
     * @return the path separator as a single-character string
     */
    public String getOsSeparator() {
        String sep = java.io.File.separator;
        LOGGER.info("OS path separator: '{}'", sep);
        return sep;
    }
}
