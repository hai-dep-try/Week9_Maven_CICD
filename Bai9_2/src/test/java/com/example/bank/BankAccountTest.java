package com.example.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

    /** Creates a fresh account before each test. */
    @BeforeEach
    void setUp() {
        account = new BankAccount("TestUser", 500.0);
    }

    @Nested
    @DisplayName("Constructor")
    class ConstructorTests {

        @Test
        @DisplayName("creates account with correct initial balance")
        void initialBalance() {
            assertEquals(500.0, account.getBalance(), 1e-9);
        }

        @Test
        @DisplayName("creates account with zero balance when no amount given")
        void zeroBalance() {
            BankAccount empty = new BankAccount("Empty");
            assertEquals(0.0, empty.getBalance(), 1e-9);
        }

        @Test
        @DisplayName("rejects negative initial balance")
        void negativeInitialBalance() {
            assertThrows(IllegalArgumentException.class,
                    () -> new BankAccount("Bad", -100.0));
        }
    }

    @Nested
    @DisplayName("deposit()")
    class DepositTests {

        @Test
        @DisplayName("increases balance by deposited amount")
        void depositIncreasesBalance() {
            account.deposit(200.0);
            assertEquals(700.0, account.getBalance(), 1e-9);
        }

        @Test
        @DisplayName("records transaction in history")
        void depositRecorded() {
            account.deposit(100.0);
            assertFalse(account.getHistory().isEmpty());
            assertEquals(TransactionType.DEPOSIT, account.getHistory().get(0).getType());
        }

        @Test
        @DisplayName("rejects zero amount")
        void depositZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
        }

        @Test
        @DisplayName("rejects negative amount")
        void depositNegativeThrows() {
            assertThrows(IllegalArgumentException.class, () -> account.deposit(-50));
        }
    }

    @Nested
    @DisplayName("withdraw()")
    class WithdrawTests {

        @Test
        @DisplayName("decreases balance by withdrawn amount")
        void withdrawDecreases() {
            account.withdraw(100.0);
            assertEquals(400.0, account.getBalance(), 1e-9);
        }

        @Test
        @DisplayName("rejects withdrawal exceeding balance")
        void insufficientFunds() {
            assertThrows(IllegalStateException.class, () -> account.withdraw(9999.0));
        }

        @Test
        @DisplayName("rejects zero amount")
        void withdrawZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> account.withdraw(0));
        }

        @Test
        @DisplayName("exact balance withdrawal leaves zero")
        void withdrawAll() {
            account.withdraw(500.0);
            assertEquals(0.0, account.getBalance(), 1e-9);
        }
    }

    @Nested
    @DisplayName("getHistory()")
    class HistoryTests {

        @Test
        @DisplayName("history is unmodifiable")
        void historyIsUnmodifiable() {
            assertThrows(UnsupportedOperationException.class,
                    () -> account.getHistory().add(null));
        }

        @Test
        @DisplayName("multiple operations appear in order")
        void historyOrder() {
            account.deposit(100.0);
            account.withdraw(50.0);
            assertEquals(2, account.getHistory().size());
            assertEquals(TransactionType.DEPOSIT, account.getHistory().get(0).getType());
            assertEquals(TransactionType.WITHDRAWAL, account.getHistory().get(1).getType());
        }
    }

    @Test
    @DisplayName("accountId is non-null and non-empty")
    void accountIdNotEmpty() {
        assertTrue(account.getAccountId() != null && !account.getAccountId().isEmpty());
    }
}
