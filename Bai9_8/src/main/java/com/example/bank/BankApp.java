package com.example.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry point for the BankSystem executable JAR.
 *
 * <p>Invoked by the JVM when running:
 * <pre>java -jar target/BankSystem-Executable.jar</pre>
 *
 * <p>The Main-Class attribute in META-INF/MANIFEST.MF points to this class,
 * configured via maven-jar-plugin and preserved by maven-shade-plugin.</p>
 */
public class BankApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankApp.class);

    /**
     * Application entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        LOGGER.info("╔══════════════════════════════════════╗");
        LOGGER.info("║   BankSystem – Executable JAR Demo   ║");
        LOGGER.info("║   Running via: java -jar *.jar       ║");
        LOGGER.info("╚══════════════════════════════════════╝");
        LOGGER.info("Java version : {}", System.getProperty("java.version"));
        LOGGER.info("OS           : {}", System.getProperty("os.name"));

        BankService service = new BankService();

        // ── Create accounts ────────────────────────────────────────
        BankAccount alice = new BankAccount("Alice", 2000.0);
        BankAccount bob   = new BankAccount("Bob",    500.0);

        // ── Perform operations ─────────────────────────────────────
        alice.deposit(500.0);
        bob.deposit(200.0);
        alice.withdraw(300.0);

        // ── Transfer between accounts ──────────────────────────────
        service.transfer(alice, bob, 800.0);

        // ── Print statements ───────────────────────────────────────
        LOGGER.info("");
        service.printStatement(alice);
        LOGGER.info("");
        service.printStatement(bob);

        // ── Demonstrate error handling ─────────────────────────────
        LOGGER.info("");
        LOGGER.info(">>> Attempting withdrawal beyond balance...");
        try {
            bob.withdraw(99999.0);
        } catch (IllegalStateException e) {
            LOGGER.warn("Caught expected error: {}", e.getMessage());
        }

        LOGGER.info("");
        LOGGER.info("╔══════════════════════════════════════╗");
        LOGGER.info("║          Demo complete.              ║");
        LOGGER.info("╚══════════════════════════════════════╝");
    }
}
