package com.example.bank;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a single bank transaction (immutable value object).
 */
public final class Transaction {

    private static final Logger LOGGER = LoggerFactory.getLogger(Transaction.class);

    private final String id;
    private final TransactionType type;
    private final double amount;
    private final LocalDateTime timestamp;
    private final String description;

    /**
     * Constructs a Transaction with all fields.
     *
     * @param id          unique identifier
     * @param type        the transaction type
     * @param amount      monetary amount (positive)
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
        LOGGER.debug("Transaction created: id={} type={} amount={}", id, type, amount);
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
     * Returns when this transaction occurred.
     *
     * @return timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the human-readable description.
     *
     * @return description string
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a formatted string representation of this transaction.
     *
     * @return formatted transaction string
     */
    @Override
    public String toString() {
        return String.format("[%s] %s %.2f – %s @ %s",
                id, type, amount, description, timestamp);
    }
}
