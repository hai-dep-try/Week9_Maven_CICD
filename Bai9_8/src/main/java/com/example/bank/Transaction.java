package com.example.bank;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Immutable value object representing a single bank transaction.
 */
public final class Transaction {

    private static final Logger LOGGER = LoggerFactory.getLogger(Transaction.class);

    private final String id;
    private final TransactionType type;
    private final double amount;
    private final LocalDateTime timestamp;
    private final String description;

    /**
     * Constructs a new Transaction.
     *
     * @param id          unique transaction identifier
     * @param type        the type of transaction
     * @param amount      monetary amount (must be positive)
     * @param timestamp   when the transaction occurred
     * @param description human-readable description
     */
    public Transaction(String id, TransactionType type, double amount,
            LocalDateTime timestamp, String description) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
        LOGGER.debug("Transaction created: {} {} {}", id, type, amount);
    }

    /**
     * Returns the transaction identifier.
     *
     * @return transaction id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the transaction type.
     *
     * @return transaction type
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * Returns the monetary amount.
     *
     * @return amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the timestamp of this transaction.
     *
     * @return timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the human-readable description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a formatted string representation.
     *
     * @return formatted transaction info
     */
    @Override
    public String toString() {
        return String.format("[%s] %-10s %8.2f  %s", id, type, amount, description);
    }
}
