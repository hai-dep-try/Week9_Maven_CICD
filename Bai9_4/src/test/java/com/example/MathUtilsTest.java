package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link MathUtils} – runs on all matrix OSes.
 */
@DisplayName("MathUtils Test Suite")
class MathUtilsTest {

    private MathUtils math;

    @BeforeEach
    void setUp() {
        math = new MathUtils();
    }

    @Nested
    @DisplayName("add()")
    class AddTests {

        @Test
        @DisplayName("3 + 5 = 8")
        void addPositive() {
            assertEquals(8, math.add(3, 5));
        }

        @Test
        @DisplayName("positive + negative")
        void addWithNegative() {
            assertEquals(-2, math.add(3, -5));
        }

        @Test
        @DisplayName("n + 0 = n")
        void addZero() {
            assertEquals(7, math.add(7, 0));
        }
    }

    @Nested
    @DisplayName("subtract()")
    class SubtractTests {

        @Test
        @DisplayName("10 - 4 = 6")
        void subtractNormal() {
            assertEquals(6, math.subtract(10, 4));
        }

        @Test
        @DisplayName("result can be negative")
        void subtractLarger() {
            assertEquals(-3, math.subtract(2, 5));
        }
    }

    @Nested
    @DisplayName("multiply()")
    class MultiplyTests {

        @Test
        @DisplayName("6 * 7 = 42")
        void multiplyPositive() {
            assertEquals(42, math.multiply(6, 7));
        }

        @Test
        @DisplayName("n * 0 = 0")
        void multiplyByZero() {
            assertEquals(0, math.multiply(999, 0));
        }
    }

    @Nested
    @DisplayName("divide()")
    class DivideTests {

        @Test
        @DisplayName("15 / 4 = 3.75")
        void divideNormal() {
            assertEquals(3.75, math.divide(15, 4), 1e-9);
        }

        @Test
        @DisplayName("throws on divide-by-zero")
        void divideByZeroThrows() {
            assertThrows(ArithmeticException.class, () -> math.divide(10, 0));
        }
    }

    @Nested
    @DisplayName("isEven()")
    class IsEvenTests {

        @Test
        void evenNumber() {
            assertTrue(math.isEven(8));
        }

        @Test
        void oddNumber() {
            assertFalse(math.isEven(7));
        }

        @Test
        void zero() {
            assertTrue(math.isEven(0));
        }
    }

    @Nested
    @DisplayName("factorial()")
    class FactorialTests {

        @Test
        void factorialZero() {
            assertEquals(1L, math.factorial(0));
        }

        @Test
        void factorialSix() {
            assertEquals(720L, math.factorial(6));
        }

        @Test
        void factorialNegativeThrows() {
            assertThrows(IllegalArgumentException.class, () -> math.factorial(-1));
        }
    }

    @Nested
    @DisplayName("isPrime()")
    class IsPrimeTests {

        @Test
        void primeNumber() {
            assertTrue(math.isPrime(13));
        }

        @Test
        void one() {
            assertFalse(math.isPrime(1));
        }

        @Test
        void composite() {
            assertFalse(math.isPrime(9));
        }
    }
}
