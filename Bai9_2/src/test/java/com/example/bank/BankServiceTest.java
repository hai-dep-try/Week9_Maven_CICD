package com.example.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link BankService}.
 */
@DisplayName("BankService Test Suite")
class BankServiceTest {

    private BankService service;
    private BankAccount alice;
    private BankAccount bob;

    /** Sets up two accounts and a service before each test. */
    @BeforeEach
    void setUp() {
        service = new BankService();
        alice = new BankAccount("Alice", 1000.0);
        bob = new BankAccount("Bob", 200.0);
    }

    @Test
    @DisplayName("transfer moves amount from source to target")
    void transferMovesBalance() {
        service.transfer(alice, bob, 300.0);
        assertEquals(700.0, alice.getBalance(), 1e-9);
        assertEquals(500.0, bob.getBalance(), 1e-9);
    }

    @Test
    @DisplayName("transfer rejects negative amount")
    void transferNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.transfer(alice, bob, -10));
    }

    @Test
    @DisplayName("transfer rejects zero amount")
    void transferZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.transfer(alice, bob, 0));
    }

    @Test
    @DisplayName("transfer fails when source has insufficient funds")
    void transferInsufficientFunds() {
        assertThrows(IllegalStateException.class, () -> service.transfer(alice, bob, 9999.0));
    }

    @Test
    @DisplayName("printStatement does not throw for account with history")
    void printStatementWithHistory() {
        alice.deposit(100.0);
        alice.withdraw(50.0);
        service.printStatement(alice);
        // No exception = pass
    }

    @Test
    @DisplayName("printStatement does not throw for empty account")
    void printStatementEmpty() {
        BankAccount empty = new BankAccount("Empty");
        service.printStatement(empty);
        // No exception = pass
    }
}
