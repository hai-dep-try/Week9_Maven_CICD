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
        LOGGER.info("Account created: id={}, holder={}", accountId, accountHolder);
    }

    /**
     * Creates a BankAccount with a specified initial balance.
     *
     * @param accountHolder  name of the account owner
     * @param initialBalance starting balance; must be >= 0
     * @throws IllegalArgumentException if initialBalance is negative
     */
    public BankAccount(String accountHolder, double initialBalance) {
        this(accountHolder);
        if (initialBalance < 0) {
            LOGGER.error("Negative initial balance={} for holder={}", initialBalance, accountHolder);
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.balance = initialBalance;
    }

    /**
     * Deposits a positive amount into this account.
     *
     * @param amount the amount to deposit; must be > 0
     * @throws IllegalArgumentException if amount <= 0
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            LOGGER.warn("Deposit rejected: non-positive amount={}", amount);
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
        history.add(newTx(TransactionType.DEPOSIT, amount, "Deposit by " + accountHolder));
        LOGGER.info("Deposit OK: id={}, amount={}, newBalance={}", accountId, amount, balance);
    }

    /**
     * Withdraws a positive amount from this account.
     *
     * @param amount the amount to withdraw; must be > 0 and <= balance
     * @throws IllegalArgumentException if amount <= 0
     * @throws IllegalStateException    if insufficient funds
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            LOGGER.warn("Withdrawal rejected: non-positive amount={}", amount);
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            LOGGER.warn("Insufficient funds: requested={}, balance={}", amount, balance);
            throw new IllegalStateException("Insufficient funds");
        }
        balance -= amount;
        history.add(newTx(TransactionType.WITHDRAWAL, amount, "Withdrawal by " + accountHolder));
        LOGGER.info("Withdrawal OK: id={}, amount={}, newBalance={}", accountId, amount, balance);
    }

    /**
     * Returns the current balance.
     *
     * @return current balance
     */
    public double getBalance() {
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
     * @return immutable transaction list
     */
    public List<Transaction> getHistory() {
        return Collections.unmodifiableList(history);
    }

    /**
     * Returns a formatted string representation.
     *
     * @return account summary string
     */
    @Override
    public String toString() {
        return String.format("BankAccount{id=%s, holder='%s', balance=%.2f}",
                accountId, accountHolder, balance);
    }

    /**
     * Creates a new Transaction with a generated ID.
     *
     * @param type        transaction type
     * @param amount      amount
     * @param description description
     * @return new Transaction instance
     */
    private Transaction newTx(TransactionType type, double amount, String description) {
        String txId = "TX-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return new Transaction(txId, type, amount, LocalDateTime.now(), description);
    }
}
