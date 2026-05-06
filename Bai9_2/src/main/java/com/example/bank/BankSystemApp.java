package com.example.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demonstration entry point for the BankSystem application.
 *
 * <p>Exercises the core flows: account creation, deposit, withdrawal,
 * inter-account transfer, and statement printing.</p>
 */
public class BankSystemApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankSystemApp.class);

    /**
     * Application entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        LOGGER.info("=== BankSystem Demo Start ===");

        BankService service = new BankService();

        BankAccount alice = new BankAccount("Alice", 1000.0);
        BankAccount bob = new BankAccount("Bob", 500.0);

        alice.deposit(200.0);
        bob.deposit(100.0);

        alice.withdraw(150.0);

        service.transfer(alice, bob, 300.0);

        service.printStatement(alice);
        service.printStatement(bob);

        LOGGER.info("=== BankSystem Demo End ===");
    }
}
