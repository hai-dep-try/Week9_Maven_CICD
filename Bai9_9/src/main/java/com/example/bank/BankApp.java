package com.example.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application entry point demonstrating professional logging output.
 *
 * <p>Zero {@code System.out.println()} calls anywhere in this codebase.
 * All output is routed through SLF4J → Logback → Console + File.</p>
 */
public class BankApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankApp.class);

    /**
     * Main entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        LOGGER.info("=== BankSystem Logging Demo Start ===");
        LOGGER.info("Logback writes to CONSOLE and logs/bank.log simultaneously");

        BankService service = new BankService();

        BankAccount alice = new BankAccount("Alice", 1000.0);
        BankAccount bob   = new BankAccount("Bob",    300.0);

        alice.deposit(500.0);
        bob.deposit(100.0);
        alice.withdraw(200.0);
        service.transfer(alice, bob, 400.0);

        service.printStatement(alice);
        service.printStatement(bob);

        // Demonstrate ERROR-level log
        LOGGER.info("Attempting transfer beyond balance to trigger WARN log...");
        try {
            service.transfer(bob, alice, 99999.0);
        } catch (IllegalStateException e) {
            // ERROR: unexpected exception in business logic – needs investigation
            LOGGER.error("Transfer failed with exception: {}", e.getMessage());
        }

        LOGGER.info("=== BankSystem Logging Demo End ===");
    }
}
