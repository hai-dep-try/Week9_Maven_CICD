package com.example.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link BankAccount} and {@link BankService}.
 */
@DisplayName("BankSystem Test Suite")
class BankSystemTest {

    private BankAccount alice;
    private BankAccount bob;
    private BankService service;

    @BeforeEach
    void setUp() {
        alice   = new BankAccount("Alice", 1000.0);
        bob     = new BankAccount("Bob",    200.0);
        service = new BankService();
    }

    @Nested
    @DisplayName("BankAccount.deposit()")
    class DepositTests {

        @Test
        void depositIncreasesBalance() {
            alice.deposit(500.0);
            assertEquals(1500.0, alice.getBalance(), 1e-9);
        }

        @Test
        void depositZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> alice.deposit(0));
        }

        @Test
        void depositNegativeThrows() {
            assertThrows(IllegalArgumentException.class, () -> alice.deposit(-100));
        }
    }

    @Nested
    @DisplayName("BankAccount.withdraw()")
    class WithdrawTests {

        @Test
        void withdrawDecreases() {
            alice.withdraw(400.0);
            assertEquals(600.0, alice.getBalance(), 1e-9);
        }

        @Test
        void withdrawInsufficientFunds() {
            assertThrows(IllegalStateException.class, () -> alice.withdraw(9999.0));
        }

        @Test
        void withdrawZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> alice.withdraw(0));
        }
    }

    @Nested
    @DisplayName("BankService.transfer()")
    class TransferTests {

        @Test
        void transferMovesBalance() {
            service.transfer(alice, bob, 300.0);
            assertEquals(700.0, alice.getBalance(), 1e-9);
            assertEquals(500.0, bob.getBalance(), 1e-9);
        }

        @Test
        void transferZeroThrows() {
            assertThrows(IllegalArgumentException.class,
                () -> service.transfer(alice, bob, 0));
        }

        @Test
        void transferInsufficientFundsThrows() {
            assertThrows(IllegalStateException.class,
                () -> service.transfer(alice, bob, 9999.0));
        }
    }

    @Test
    @DisplayName("negative initial balance throws")
    void negativeInitialBalanceThrows() {
        assertThrows(IllegalArgumentException.class,
            () -> new BankAccount("Bad", -100.0));
    }
}
