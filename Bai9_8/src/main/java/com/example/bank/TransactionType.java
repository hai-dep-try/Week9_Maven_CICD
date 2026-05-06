package com.example.bank;

/**
 * Enum representing the type of a bank transaction.
 */
public enum TransactionType {

    /** Money deposited into an account. */
    DEPOSIT,

    /** Money withdrawn from an account. */
    WITHDRAWAL,

    /** Money transferred between accounts. */
    TRANSFER
}
