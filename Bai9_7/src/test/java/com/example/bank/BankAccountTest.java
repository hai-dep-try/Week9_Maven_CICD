package com.example.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link BankAccount}.
 */
@DisplayName("BankAccount Test Suite")
class BankAccountTest {

    private BankAccount account;

    /** Initialises a fresh account before each test. */
    @BeforeEach
    void setUp() {
        account = new BankAccount("Alice", 500.0);
    }

    @Nested
    @DisplayName("deposit()")
    class DepositTests {

        @Test
        void depositIncreasesBalance() {
            account.deposit(200.0);
            assertEquals(700.0, account.getBalance(), 1e-9);
        }

        @Test
        void depositZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
        }

        @Test
        void depositNegativeThrows() {
            assertThrows(IllegalArgumentException.class, () -> account.deposit(-50));
        }
    }

    @Nested
    @DisplayName("withdraw()")
    class WithdrawTests {

        @Test
        void withdrawDecreases() {
            account.withdraw(100.0);
            assertEquals(400.0, account.getBalance(), 1e-9);
        }

        @Test
        void withdrawAllBalance() {
            account.withdraw(500.0);
            assertEquals(0.0, account.getBalance(), 1e-9);
        }

        @Test
        void withdrawInsufficientFunds() {
            assertThrows(IllegalStateException.class, () -> account.withdraw(9999.0));
        }

        @Test
        void withdrawZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> account.withdraw(0));
        }
    }

    @Test
    @DisplayName("accountId is non-null and non-empty")
    void accountIdPresent() {
        assertTrue(account.getAccountId() != null && !account.getAccountId().isEmpty());
    }

    @Test
    @DisplayName("negative initial balance throws")
    void negativeInitialBalanceThrows() {
        assertThrows(IllegalArgumentException.class,
            () -> new BankAccount("Bob", -100.0));
    }
}
