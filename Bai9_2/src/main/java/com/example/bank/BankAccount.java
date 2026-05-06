package com.example.bank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a bank account with deposit, withdrawal, and query capabilities.
 *
 * <p>All state-changing operations are validated and recorded as
 * immutable {@link Transaction} entries in an internal history list.
 * Every significant event is logged via SLF4J for observability.</p>
 */
public class BankAccount {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccount.class);

    private final String accountId;
    private final String accountHolder;
    private double balance;
    private final List<Transaction> history;

    /**
     * Creates a new BankAccount with zero initial balance.
     *
     * @param accountHolder the full name of the account owner
     */
    public BankAccount(String accountHolder) {
        this.accountId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.accountHolder = accountHolder;
        this.balance = 0.0;
        this.history = new ArrayList<>();
        // INFO: account lifecycle event – always useful in production logs
        LOGGER.info("Account created: id={}, holder={}", accountId, accountHolder);
    }

    /**
     * Creates a new BankAccount with a specified initial balance.
     *
     * @param accountHolder  the full name of the account owner
     * @param initialBalance starting balance; must be &gt;= 0
     * @throws IllegalArgumentException if initialBalance is negative
     */
    public BankAccount(String accountHolder, double initialBalance) {
        this(accountHolder);
        if (initialBalance < 0) {
            // ERROR: programmer misuse – negative balance is an invalid state
            LOGGER.error("Account creation rejected: negative initialBalance={} for holder={}",
                    initialBalance, accountHolder);
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.balance = initialBalance;
        LOGGER.info("Initial balance applied: accountId={}, balance={}", accountId, initialBalance);
    }

    /**
     * Deposits a positive amount into this account.
     *
     * @param amount the amount to deposit; must be &gt; 0
     * @throws IllegalArgumentException if amount is zero or negative
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            // WARN: caller passed bad input – recoverable but noteworthy
            LOGGER.warn("Deposit rejected: non-positive amount={}, accountId={}", amount, accountId);
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
        Transaction tx = buildTx(TransactionType.DEPOSIT, amount, "Deposit");
        history.add(tx);
        // INFO: every successful deposit is a business event worth recording
        LOGGER.info("Deposit OK: accountId={}, amount={}, newBalance={}", accountId, amount, balance);
        // DEBUG: full transaction object – useful during development only
        LOGGER.debug("Transaction recorded: {}", tx);
    }

    /**
     * Withdraws a positive amount from this account if funds are sufficient.
     *
     * @param amount the amount to withdraw; must be &gt; 0 and &lt;= current balance
     * @throws IllegalArgumentException if amount is zero or negative
     * @throws IllegalStateException    if the account has insufficient funds
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            LOGGER.warn("Withdrawal rejected: non-positive amount={}, accountId={}",
                    amount, accountId);
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            // WARN: business rule violation – insufficient funds is expected in prod
            LOGGER.warn("Withdrawal rejected: insufficient funds. requested={}, balance={}, id={}",
                    amount, balance, accountId);
            throw new IllegalStateException("Insufficient funds");
        }
        balance -= amount;
        Transaction tx = buildTx(TransactionType.WITHDRAWAL, amount, "Withdrawal");
        history.add(tx);
        LOGGER.info("Withdrawal OK: accountId={}, amount={}, newBalance={}",
                accountId, amount, balance);
        LOGGER.debug("Transaction recorded: {}", tx);
    }

    /**
     * Returns the current balance of this account.
     *
     * @return current balance as a double
     */
    public double getBalance() {
        // DEBUG: balance checks are high-frequency; keep at debug to avoid noise
        LOGGER.debug("Balance queried: accountId={}, balance={}", accountId, balance);
        return balance;
    }

    /**
     * Returns the unique identifier of this account.
     *
     * @return account ID string
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Returns the name of the account holder.
     *
     * @return account holder name
     */
    public String getAccountHolder() {
        return accountHolder;
    }

    /**
     * Returns an unmodifiable view of the transaction history.
     *
     * @return immutable list of all transactions in chronological order
     */
    public List<Transaction> getHistory() {
        return Collections.unmodifiableList(history);
    }

    /**
     * Returns a brief string representation of this account.
     *
     * @return formatted string with id, holder, and balance
     */
    @Override
    public String toString() {
        return String.format("BankAccount{id=%s, holder=%s, balance=%.2f}",
                accountId, accountHolder, balance);
    }

    /**
     * Creates a Transaction record for the given type and amount.
     *
     * @param type        the transaction type
     * @param amount      the monetary amount
     * @param description short description prefix
     * @return a new immutable Transaction
     */
    private Transaction buildTx(TransactionType type, double amount, String description) {
        String txId = "TX-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return new Transaction(txId, type, amount, LocalDateTime.now(),
                description + " by " + accountHolder);
    }
}
