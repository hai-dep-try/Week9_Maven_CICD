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
 * Cross-platform path tests for {@link FileUtils}.
 *
 * <h2>Demonstration: "It works on my machine"</h2>
 *
 * <p><strong>BROKEN version (Windows-only path – fails on Linux/macOS):</strong></p>
 * <pre>
 * {@code
 * // This test FAILS on Linux/macOS because:
 * // - Paths.get("data\\config\\app.properties") on Linux treats the
 * //   entire string as ONE segment (backslash is a valid filename char)
 * // - Result: path has 1 component, not 3
 * // - getNameCount() returns 1, not 3 => AssertionError
 *
 * @Test
 * void brokenWindowsPath() {
 *     // Hardcoded Windows backslash separator:
 *     String windowsPath = "data\\config\\app.properties";
 *     Path p = Paths.get(windowsPath);
 *     // On Windows: nameCount = 3  (passes)
 *     // On Linux  : nameCount = 1  (FAILS – backslash is part of filename!)
 *     assertEquals(3, p.getNameCount());
 * }
 * }
 * </pre>
 *
 * <p><strong>FIXED version below uses {@code Paths.get(first, more...)}
 * which lets the JVM choose the correct separator.</strong></p>
 */
@DisplayName("FileUtils Cross-Platform Path Tests")
class FileUtilsTest {

    private FileUtils fileUtils;

    @BeforeEach
    void setUp() {
        fileUtils = new FileUtils();
    }

    // =========================================================================
    //  FIXED: buildPath – uses Paths.get(first, more...) NOT hardcoded slashes
    // =========================================================================

    @Nested
    @DisplayName("buildPath() – cross-platform (FIXED with Path API)")
    class BuildPathTests {

        @Test
        @DisplayName("builds 3-segment path correctly on any OS")
        void buildThreeSegments() {
            // CORRECT: pass segments separately, let JVM use OS separator
            Path path = fileUtils.buildPath("data", "config", "app.properties");

            assertNotNull(path);
            // Path always has 3 name components regardless of OS separator
            assertEquals(3, path.getNameCount(),
                "Path must have 3 segments regardless of OS");
            assertEquals("data", path.getName(0).toString());
            assertEquals("config", path.getName(1).toString());
            assertEquals("app.properties", path.getName(2).toString());
        }

        @Test
        @DisplayName("single segment path has 1 component")
        void buildSingleSegment() {
            Path path = fileUtils.buildPath("README.md");
            assertEquals(1, path.getNameCount());
            assertEquals("README.md", path.getFileName().toString());
        }

        @Test
        @DisplayName("toString() uses OS-specific separator")
        void toStringUsesOsSeparator() {
            Path path = fileUtils.buildPath("logs", "app.log");
            String str = path.toString();
            // The string representation depends on OS:
            //   Windows: "logs\app.log"
            //   Linux  : "logs/app.log"
            // We verify it contains the OS separator, not a hardcoded one
            String sep = java.io.File.separator;
            assertEquals("logs" + sep + "app.log", str,
                "Path string must use the OS-specific separator: '" + sep + "'");
        }

        @Test
        @DisplayName("throws on blank root segment")
        void blankRootThrows() {
            assertThrows(IllegalArgumentException.class,
                () -> fileUtils.buildPath("   "));
        }

        @Test
        @DisplayName("throws on null root segment")
        void nullRootThrows() {
            assertThrows(IllegalArgumentException.class,
                () -> fileUtils.buildPath(null));
        }
    }

    // =========================================================================
    //  FIXED: getExtension – uses Path.getFileName().toString(), not string split
    // =========================================================================

    @Nested
    @DisplayName("getExtension() – cross-platform (FIXED with Path.getFileName)")
    class GetExtensionTests {

        @Test
        @DisplayName("returns 'java' for a .java file path")
        void javaExtension() {
            // Build path with platform-safe API, then extract extension
            Path p = fileUtils.buildPath("src", "main", "MathUtils.java");
            assertEquals("java", fileUtils.getExtension(p));
        }

        @Test
        @DisplayName("returns 'xml' for a .xml file")
        void xmlExtension() {
            Path p = fileUtils.buildPath("src", "main", "resources", "logback.xml");
            assertEquals("xml", fileUtils.getExtension(p));
        }

        @Test
        @DisplayName("returns empty string for file with no extension")
        void noExtension() {
            Path p = fileUtils.buildPath("Makefile");
            assertEquals("", fileUtils.getExtension(p));
        }

        @Test
        @DisplayName("extension is returned in lowercase")
        void extensionIsLowercase() {
            Path p = Paths.get("Report.PDF");
            assertEquals("pdf", fileUtils.getExtension(p));
        }

        @Test
        @DisplayName("throws on null path")
        void nullPathThrows() {
            assertThrows(IllegalArgumentException.class,
                () -> fileUtils.getExtension(null));
        }
    }

    // =========================================================================
    //  OS info test – demonstrates what each matrix runner reports
    // =========================================================================

    @Nested
    @DisplayName("OS separator – observed values per matrix runner")
    class OsSeparatorTests {

        @Test
        @DisplayName("separator is either '/' (Unix) or '\\' (Windows)")
        void separatorIsKnownValue() {
            String sep = fileUtils.getOsSeparator();
            // Both "/" and "\" are valid – the test is OS-agnostic
            boolean isUnix    = "/".equals(sep);
            boolean isWindows = "\\".equals(sep);
            assertEquals(true, isUnix || isWindows,
                "File.separator must be '/' or '\\', was: '" + sep + "'");
        }

        @Test
        @DisplayName("os.name system property is non-empty")
        void osNameIsPresent() {
            String osName = System.getProperty("os.name");
            assertNotNull(osName);
            assertEquals(false, osName.isBlank(),
                "os.name should not be blank");
            // In GitHub Actions matrix:
            //   ubuntu-latest  -> "Linux"
            //   windows-latest -> "Windows 10" or "Windows Server 2022"
            //   macos-latest   -> "Mac OS X"
            System.out.println("[TEST] Running on OS: " + osName);
            System.out.println("[TEST] File.separator: '" + java.io.File.separator + "'");
        }
    }
}
