package com.example.bank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a bank account with deposit, withdrawal, and history.
 *
 * <h2>Logging Strategy</h2>
 *
 * <p><strong>BEFORE (amateur – removed):</strong></p>
 * <pre>
 *   // ❌ System.out.println – no timestamp, no level, no structure
 *   System.out.println("Depositing " + amount + " into " + accountId);
 *
 *   // ❌ String concatenation in logger – always builds the String,
 *   //    even when the DEBUG level is disabled (wastes CPU + memory)
 *   LOGGER.debug("Balance after deposit: " + balance);
 * </pre>
 *
 * <p><strong>AFTER (professional – used here):</strong></p>
 * <pre>
 *   // ✅ Parameterized {} – String built ONLY if level is enabled
 *   LOGGER.info("Deposit OK: accountId={}, amount={}, newBalance={}", id, amount, balance);
 *
 *   // ✅ ERROR for exceptions – signals that investigation is needed
 *   LOGGER.error("Negative initial balance={} rejected for holder={}", balance, holder);
 * </pre>
 *
 * <h2>Level Guide</h2>
 * <ul>
 *   <li>{@code DEBUG} – fine-grained flow, disabled in production</li>
 *   <li>{@code INFO}  – lifecycle milestones (account created, tx succeeded)</li>
 *   <li>{@code WARN}  – recoverable business anomalies (bad input, low balance)</li>
 *   <li>{@code ERROR} – unrecoverable or unexpected states</li>
 * </ul>
 */
public class BankAccount {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccount.class);

    private final String accountId;
    private final String accountHolder;
    private double balance;
    private final List<Transaction> history;

    /**
     * Creates a BankAccount with zero initial balance.
     *
     * @param accountHolder name of the account owner
     */
    public BankAccount(String accountHolder) {
        this.accountId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.accountHolder = accountHolder;
        this.balance = 0.0;
        this.history = new ArrayList<>();

        // INFO: account creation is a lifecycle milestone worth tracking in prod
        LOGGER.info("Account created: accountId={}, holder={}", accountId, accountHolder);
    }

    /**
     * Creates a BankAccount with the given initial balance.
     *
     * @param accountHolder  name of the account owner
     * @param initialBalance starting balance; must be >= 0
     * @throws IllegalArgumentException if initialBalance is negative
     */
    public BankAccount(String accountHolder, double initialBalance) {
        this(accountHolder);
        if (initialBalance < 0) {
            // ERROR: caller violated a contract; needs immediate attention
            LOGGER.error("Account creation rejected: negative initialBalance={} for holder={}",
                    initialBalance, accountHolder);
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.balance = initialBalance;
        // INFO: initial balance is a significant state to record
        LOGGER.info("Initial balance applied: accountId={}, balance={}", accountId, initialBalance);
    }

    /**
     * Deposits a positive amount into this account.
     *
     * @param amount the amount to deposit; must be > 0
     * @throws IllegalArgumentException if amount is zero or negative
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            // WARN: caller sent bad input – recoverable, but suspicious
            LOGGER.warn("Deposit rejected: non-positive amount={}, accountId={}", amount, accountId);
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
        Transaction tx = newTx(TransactionType.DEPOSIT, amount, "Deposit");
        history.add(tx);

        // INFO: successful deposit is a key business milestone
        LOGGER.info("Deposit OK: accountId={}, amount={}, newBalance={}", accountId, amount, balance);

        // DEBUG: full tx object – useful during development, disabled in prod
        LOGGER.debug("Transaction recorded: {}", tx);
    }

    /**
     * Withdraws a positive amount from this account.
     *
     * @param amount the amount to withdraw; must be > 0 and <= current balance
     * @throws IllegalArgumentException if amount <= 0
     * @throws IllegalStateException    if insufficient funds
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            LOGGER.warn("Withdrawal rejected: non-positive amount={}, accountId={}",
                    amount, accountId);
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            // WARN: business rule violation; not a system error but worth tracking
            LOGGER.warn("Insufficient funds: accountId={}, requested={}, available={}",
                    accountId, amount, balance);
            throw new IllegalStateException("Insufficient funds");
        }
        balance -= amount;
        Transaction tx = newTx(TransactionType.WITHDRAWAL, amount, "Withdrawal");
        history.add(tx);

        LOGGER.info("Withdrawal OK: accountId={}, amount={}, newBalance={}",
                accountId, amount, balance);
        LOGGER.debug("Transaction recorded: {}", tx);
    }

    /**
     * Returns the current balance.
     *
     * @return current balance
     */
    public double getBalance() {
        // DEBUG: balance query is high-frequency; keep out of INFO to avoid noise
        LOGGER.debug("Balance queried: accountId={}, balance={}", accountId, balance);
        return balance;
    }

    /**
     * Returns the unique account identifier.
     *
     * @return account id
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Returns the account holder's name.
     *
     * @return holder name
     */
    public String getAccountHolder() {
        return accountHolder;
    }

    /**
     * Returns an unmodifiable view of the transaction history.
     *
     * @return immutable list of transactions
     */
    public List<Transaction> getHistory() {
        return Collections.unmodifiableList(history);
    }

    /**
     * Returns a summary string for this account.
     *
     * @return formatted account string
     */
    @Override
    public String toString() {
        return String.format("BankAccount{id=%s, holder='%s', balance=%.2f}",
                accountId, accountHolder, balance);
    }

    /**
     * Creates a new Transaction with an auto-generated ID.
     *
     * @param type   the transaction type
     * @param amount monetary amount
     * @param label  description prefix
     * @return a new Transaction
     */
    private Transaction newTx(TransactionType type, double amount, String label) {
        String txId = "TX-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return new Transaction(txId, type, amount,
                LocalDateTime.now(), label + " by " + accountHolder);
    }
}
