package com.example.bank;

/**
 * Enum representing the type of a bank transaction.
 */
public enum TransactionType {

    /** Money added to account. */
    DEPOSIT,

    /** Money removed from account. */
    WITHDRAWAL,

    /** Money moved between accounts. */
    TRANSFER
}
