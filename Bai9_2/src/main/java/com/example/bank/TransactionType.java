package com.example.bank;

/**
 * Enumeration of supported financial transaction types.
 */
public enum TransactionType {

    /** Funds credited to an account. */
    DEPOSIT,

    /** Funds debited from an account. */
    WITHDRAWAL,

    /** Funds moved from one account to another. */
    TRANSFER
}
