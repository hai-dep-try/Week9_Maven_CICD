package com.example.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service layer coordinating multi-account operations.
 *
 * <p>All logging uses parameterized placeholders {} for efficiency.
 * No {@code System.out.println} is used anywhere in this codebase.</p>
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

        // INFO: transfer start – both parties logged for end-to-end traceability
        LOGGER.info("Transfer initiated: from={}, to={}, amount={}",
                source.getAccountId(), target.getAccountId(), amount);

        source.withdraw(amount);
        target.deposit(amount);

        // INFO: successful completion – confirm both new balances
        LOGGER.info("Transfer complete: from={} newBalance={}, to={} newBalance={}",
                source.getAccountId(), source.getBalance(),
                target.getAccountId(), target.getBalance());
    }

    /**
     * Prints a formatted account statement at INFO level.
     *
     * @param account the account to print
     */
    public void printStatement(BankAccount account) {
        LOGGER.info("=== Statement: {} ({}) ===",
                account.getAccountHolder(), account.getAccountId());

        if (account.getHistory().isEmpty()) {
            LOGGER.info("  (no transactions)");
        } else {
            for (Transaction tx : account.getHistory()) {
                // INFO: each transaction line is a business audit record
                LOGGER.info("  {}", tx);
            }
        }

        LOGGER.info("  Current balance: {}", String.format("%.2f", account.getBalance()));
    }
}
