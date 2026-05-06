package com.example.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service layer that orchestrates multi-account operations such as transfers.
 *
 * <p>Delegates single-account operations (deposit/withdraw) to
 * {@link BankAccount} and adds cross-account coordination and logging.</p>
 */
public class BankService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankService.class);

    /**
     * Transfers a positive amount from the source account to the target account.
     *
     * <p>The operation is atomic at the application level: if the withdrawal
     * from the source fails (e.g. insufficient funds), the target is not touched.</p>
     *
     * @param source the account to debit
     * @param target the account to credit
     * @param amount the amount to transfer; must be &gt; 0
     * @throws IllegalArgumentException if amount is zero or negative
     * @throws IllegalStateException    if source has insufficient funds
     */
    public void transfer(BankAccount source, BankAccount target, double amount) {
        if (amount <= 0) {
            LOGGER.warn("Transfer rejected: non-positive amount={}", amount);
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        // INFO: transfer start – record both parties for auditability
        LOGGER.info("Transfer initiated: from={}, to={}, amount={}",
                source.getAccountId(), target.getAccountId(), amount);
        source.withdraw(amount);
        target.deposit(amount);
        LOGGER.info("Transfer complete: from={}, to={}, amount={}",
                source.getAccountId(), target.getAccountId(), amount);
    }

    /**
     * Prints a formatted account statement to the log at INFO level.
     *
     * @param account the account whose statement should be printed
     */
    public void printStatement(BankAccount account) {
        LOGGER.info("=== Statement for {} (id={}) ===",
                account.getAccountHolder(), account.getAccountId());
        if (account.getHistory().isEmpty()) {
            LOGGER.info("  No transactions recorded.");
        } else {
            for (Transaction tx : account.getHistory()) {
                LOGGER.info("  {}", tx);
            }
        }
        LOGGER.info("  Current balance: {}", account.getBalance());
    }
}
