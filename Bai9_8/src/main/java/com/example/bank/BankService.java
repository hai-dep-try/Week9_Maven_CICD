package com.example.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service layer coordinating multi-account operations such as transfers.
 */
public class BankService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankService.class);

    /**
     * Transfers a positive amount from source to target account.
     *
     * @param source the account to debit
     * @param target the account to credit
     * @param amount the amount to transfer; must be > 0
     * @throws IllegalArgumentException if amount <= 0
     * @throws IllegalStateException    if source has insufficient funds
     */
    public void transfer(BankAccount source, BankAccount target, double amount) {
        if (amount <= 0) {
            LOGGER.warn("Transfer rejected: non-positive amount={}", amount);
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        LOGGER.info("Transfer: from={}, to={}, amount={}",
                source.getAccountId(), target.getAccountId(), amount);
        source.withdraw(amount);
        target.deposit(amount);
        LOGGER.info("Transfer complete: from={} balance={}, to={} balance={}",
                source.getAccountId(), source.getBalance(),
                target.getAccountId(), target.getBalance());
    }

    /**
     * Prints a formatted account statement via SLF4J INFO logs.
     *
     * @param account the account to print
     */
    public void printStatement(BankAccount account) {
        LOGGER.info("──── Statement: {} ({}) ────",
                account.getAccountHolder(), account.getAccountId());
        if (account.getHistory().isEmpty()) {
            LOGGER.info("  (no transactions)");
        } else {
            for (Transaction tx : account.getHistory()) {
                LOGGER.info("  {}", tx);
            }
        }
        LOGGER.info("  Balance: {}", String.format("%.2f", account.getBalance()));
    }
}
