package com.example.bank;

/**
 * Enum representing the type of a bank transaction.
 */
public enum TransactionType {

    /** Money deposited into account. */
    DEPOSIT,

    /** Money withdrawn from account. */
    WITHDRAWAL,

    /** Money transferred between accounts. */
    TRANSFER
}
