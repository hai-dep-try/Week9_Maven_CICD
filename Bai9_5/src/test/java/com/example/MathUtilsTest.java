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
 * Unit tests for {@link MathUtils}.
 * Designed to achieve and maintain over 80% JaCoCo coverage.
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
        void addPositive() {
            assertEquals(8, math.add(3, 5));
        }

        @Test
        void addNegative() {
            assertEquals(-2, math.add(3, -5));
        }

        @Test
        void addZero() {
            assertEquals(7, math.add(7, 0));
        }
    }

    @Nested
    @DisplayName("subtract()")
    class SubtractTests {

        @Test
        void subtractNormal() {
            assertEquals(6, math.subtract(10, 4));
        }

        @Test
        void subtractNegativeResult() {
            assertEquals(-3, math.subtract(2, 5));
        }
    }

    @Nested
    @DisplayName("multiply()")
    class MultiplyTests {

        @Test
        void multiplyPositive() {
            assertEquals(42, math.multiply(6, 7));
        }

        @Test
        void multiplyByZero() {
            assertEquals(0, math.multiply(999, 0));
        }
    }

    @Nested
    @DisplayName("divide()")
    class DivideTests {

        @Test
        void divideNormal() {
            assertEquals(3.75, math.divide(15, 4), 1e-9);
        }

        @Test
        void divideFraction() {
            assertEquals(0.5, math.divide(1, 2), 1e-9);
        }

        @Test
        void divideByZeroThrows() {
            assertThrows(ArithmeticException.class, () -> math.divide(10, 0));
        }
    }

    @Nested
    @DisplayName("isEven()")
    class IsEvenTests {

        @Test
        void even() {
            assertTrue(math.isEven(8));
        }

        @Test
        void odd() {
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
        void factorialOne() {
            assertEquals(1L, math.factorial(1));
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
        void prime13() {
            assertTrue(math.isPrime(13));
        }

        @Test
        void prime2() {
            assertTrue(math.isPrime(2));
        }

        @Test
        void notPrime1() {
            assertFalse(math.isPrime(1));
        }

        @Test
        void notPrime0() {
            assertFalse(math.isPrime(0));
        }

        @Test
        void compositeNumber() {
            assertFalse(math.isPrime(9));
        }

        @Test
        void compositeEven() {
            assertFalse(math.isPrime(4));
        }
    }
}
