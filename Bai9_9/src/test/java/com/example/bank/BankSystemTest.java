package com.example.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for the BankSystem.
 *
 * <p>Tests use SLF4J for structured logging.
 * No {@code System.out.println()} in tests either.</p>
 */
@DisplayName("BankSystem Logging Test Suite")
class BankSystemTest {

    // Even test classes use SLF4J for observability
    private static final Logger LOGGER = LoggerFactory.getLogger(BankSystemTest.class);

    private BankAccount alice;
    private BankAccount bob;
    private BankService service;

    /** Sets up test fixtures before each test. */
    @BeforeEach
    void setUp() {
        LOGGER.debug("Setting up test fixtures...");
        alice   = new BankAccount("Alice", 1000.0);
        bob     = new BankAccount("Bob",    200.0);
        service = new BankService();
    }

    @Nested
    @DisplayName("BankAccount.deposit()")
    class DepositTests {

        @Test
        @DisplayName("deposit increases balance correctly")
        void depositIncreasesBalance() {
            LOGGER.info("Test: deposit(500) on account with balance=1000");
            alice.deposit(500.0);
            assertEquals(1500.0, alice.getBalance(), 1e-9);
            LOGGER.info("Test PASSED: newBalance={}", alice.getBalance());
        }

        @Test
        @DisplayName("zero deposit throws IllegalArgumentException")
        void depositZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> alice.deposit(0));
        }

        @Test
        @DisplayName("negative deposit throws IllegalArgumentException")
        void depositNegativeThrows() {
            assertThrows(IllegalArgumentException.class, () -> alice.deposit(-100));
        }

        @Test
        @DisplayName("deposit creates a DEPOSIT transaction in history")
        void depositRecordedInHistory() {
            alice.deposit(300.0);
            assertEquals(1, alice.getHistory().size());
            assertEquals(TransactionType.DEPOSIT, alice.getHistory().get(0).getType());
        }
    }

    @Nested
    @DisplayName("BankAccount.withdraw()")
    class WithdrawTests {

        @Test
        @DisplayName("withdrawal decreases balance correctly")
        void withdrawDecreases() {
            alice.withdraw(400.0);
            assertEquals(600.0, alice.getBalance(), 1e-9);
        }

        @Test
        @DisplayName("withdraw exact balance leaves zero")
        void withdrawAll() {
            alice.withdraw(1000.0);
            assertEquals(0.0, alice.getBalance(), 1e-9);
        }

        @Test
        @DisplayName("insufficient funds throws IllegalStateException")
        void insufficientFunds() {
            assertThrows(IllegalStateException.class, () -> alice.withdraw(9999.0));
        }

        @Test
        @DisplayName("zero withdrawal throws IllegalArgumentException")
        void withdrawZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> alice.withdraw(0));
        }
    }

    @Nested
    @DisplayName("BankService.transfer()")
    class TransferTests {

        @Test
        @DisplayName("transfer moves correct amount between accounts")
        void transferMovesBalance() {
            LOGGER.info("Test: transfer(300) from alice={} to bob={}",
                    alice.getBalance(), bob.getBalance());
            service.transfer(alice, bob, 300.0);
            assertEquals(700.0, alice.getBalance(), 1e-9);
            assertEquals(500.0, bob.getBalance(), 1e-9);
            LOGGER.info("Test PASSED: alice={}, bob={}", alice.getBalance(), bob.getBalance());
        }

        @Test
        @DisplayName("transfer with insufficient funds throws")
        void transferInsufficientFunds() {
            assertThrows(IllegalStateException.class,
                () -> service.transfer(alice, bob, 9999.0));
        }

        @Test
        @DisplayName("transfer zero amount throws")
        void transferZeroThrows() {
            assertThrows(IllegalArgumentException.class,
                () -> service.transfer(alice, bob, 0));
        }
    }

    @Test
    @DisplayName("negative initial balance throws IllegalArgumentException")
    void negativeInitialBalanceThrows() {
        assertThrows(IllegalArgumentException.class,
            () -> new BankAccount("Bad", -100.0));
    }

    @Test
    @DisplayName("history is unmodifiable")
    void historyIsUnmodifiable() {
        alice.deposit(100.0);
        assertThrows(UnsupportedOperationException.class,
            () -> alice.getHistory().add(null));
    }

    @Test
    @DisplayName("accountId is not null or blank")
    void accountIdPresent() {
        assertTrue(alice.getAccountId() != null && !alice.getAccountId().isBlank());
    }
}
