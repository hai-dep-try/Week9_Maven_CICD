package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link FileUtils}.
 * Uses java.nio.file.Path API – cross-platform on all matrix OSes.
 */
@DisplayName("FileUtils Test Suite")
class FileUtilsTest {

    private FileUtils fileUtils;

    @BeforeEach
    void setUp() {
        fileUtils = new FileUtils();
    }

    @Nested
    @DisplayName("buildPath()")
    class BuildPathTests {

        @Test
        void threeSegments() {
            Path path = fileUtils.buildPath("data", "config", "app.properties");
            assertNotNull(path);
            assertEquals(3, path.getNameCount());
            assertEquals("data", path.getName(0).toString());
            assertEquals("config", path.getName(1).toString());
            assertEquals("app.properties", path.getName(2).toString());
        }

        @Test
        void singleSegment() {
            Path path = fileUtils.buildPath("README.md");
            assertEquals(1, path.getNameCount());
        }

        @Test
        void toStringUsesOsSeparator() {
            Path path = fileUtils.buildPath("logs", "app.log");
            String expected = "logs" + java.io.File.separator + "app.log";
            assertEquals(expected, path.toString());
        }

        @Test
        void blankRootThrows() {
            assertThrows(IllegalArgumentException.class,
                () -> fileUtils.buildPath("   "));
        }

        @Test
        void nullRootThrows() {
            assertThrows(IllegalArgumentException.class,
                () -> fileUtils.buildPath(null));
        }
    }

    @Nested
    @DisplayName("getExtension()")
    class GetExtensionTests {

        @Test
        void javaExtension() {
            Path p = fileUtils.buildPath("src", "MathUtils.java");
            assertEquals("java", fileUtils.getExtension(p));
        }

        @Test
        void xmlExtension() {
            Path p = fileUtils.buildPath("resources", "logback.xml");
            assertEquals("xml", fileUtils.getExtension(p));
        }

        @Test
        void noExtension() {
            Path p = fileUtils.buildPath("Makefile");
            assertEquals("", fileUtils.getExtension(p));
        }

        @Test
        void lowercaseExtension() {
            Path p = Paths.get("Report.PDF");
            assertEquals("pdf", fileUtils.getExtension(p));
        }

        @Test
        void nullPathThrows() {
            assertThrows(IllegalArgumentException.class,
                () -> fileUtils.getExtension(null));
        }
    }

    @Nested
    @DisplayName("getOsSeparator()")
    class SeparatorTests {

        @Test
        void separatorIsKnownValue() {
            String sep = fileUtils.getOsSeparator();
            boolean valid = "/".equals(sep) || "\\".equals(sep);
            assertEquals(true, valid,
                "File.separator must be '/' or '\\', was: '" + sep + "'");
        }
    }
}
