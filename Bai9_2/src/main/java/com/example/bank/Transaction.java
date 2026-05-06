package com.example.bank;

import java.time.LocalDateTime;

/**
 * Immutable record of a single financial transaction.
 *
 * <p>Once created a Transaction cannot be modified,
 * ensuring an accurate and tamper-proof audit trail.</p>
 */
public class Transaction {

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
     * @param amount      the monetary amount (must be positive)
     * @param timestamp   the date and time the transaction occurred
     * @param description human-readable description
     */
    public Transaction(String id, TransactionType type, double amount,
            LocalDateTime timestamp, String description) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
    }

    /**
     * Returns the unique transaction identifier.
     *
     * @return transaction ID string
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the type of this transaction.
     *
     * @return transaction type enum value
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * Returns the monetary amount of this transaction.
     *
     * @return amount as a positive double
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the timestamp when this transaction was recorded.
     *
     * @return local date-time of the transaction
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns a human-readable description of this transaction.
     *
     * @return description string
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a formatted string representation of this transaction.
     *
     * @return string in format [timestamp] TYPE amount - description
     */
    @Override
    public String toString() {
        return String.format("[%s] %s %.2f - %s", timestamp, type, amount, description);
    }
}
