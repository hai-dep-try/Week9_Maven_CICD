package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link MathUtils} – written with JUnit Jupiter 5.9.2.
 *
 * <p>JUnit 4 annotations ({@code @org.junit.Test}) are intentionally absent;
 * this class uses only the {@code org.junit.jupiter.*} namespace.</p>
 */
@DisplayName("MathUtils Test Suite")
class MathUtilsTest {

    private MathUtils math;

    @BeforeEach
    void setUp() {
        math = new MathUtils();
    }

    // -----------------------------------------------------------------------
    // add()
    // -----------------------------------------------------------------------

    @Nested
    @DisplayName("add()")
    class AddTests {

        @Test
        @DisplayName("adds two positive numbers")
        void addPositiveNumbers() {
            assertEquals(8, math.add(3, 5), "3 + 5 should be 8");
        }

        @Test
        @DisplayName("adds a positive and a negative number")
        void addWithNegative() {
            assertEquals(-2, math.add(3, -5));
        }

        @Test
        @DisplayName("adding zero returns the same number")
        void addZero() {
            assertEquals(7, math.add(7, 0));
        }
    }

    // -----------------------------------------------------------------------
    // subtract()
    // -----------------------------------------------------------------------

    @Nested
    @DisplayName("subtract()")
    class SubtractTests {

        @Test
        @DisplayName("subtracts smaller from larger")
        void subtractNormal() {
            assertEquals(6, math.subtract(10, 4));
        }

        @Test
        @DisplayName("result can be negative")
        void subtractLargerFromSmaller() {
            assertEquals(-3, math.subtract(2, 5));
        }
    }

    // -----------------------------------------------------------------------
    // multiply()
    // -----------------------------------------------------------------------

    @Nested
    @DisplayName("multiply()")
    class MultiplyTests {

        @Test
        @DisplayName("multiplies two positive numbers")
        void multiplyPositive() {
            assertEquals(42, math.multiply(6, 7));
        }

        @Test
        @DisplayName("anything multiplied by zero is zero")
        void multiplyByZero() {
            assertEquals(0, math.multiply(999, 0));
        }
    }

    // -----------------------------------------------------------------------
    // divide()
    // -----------------------------------------------------------------------

    @Nested
    @DisplayName("divide()")
    class DivideTests {

        @Test
        @DisplayName("divides two positive doubles")
        void divideNormal() {
            assertEquals(3.75, math.divide(15, 4), 1e-9);
        }

        @Test
        @DisplayName("divides with result < 1")
        void divideFraction() {
            assertEquals(0.5, math.divide(1, 2), 1e-9);
        }

        @Test
        @DisplayName("throws ArithmeticException on divide-by-zero")
        void divideByZeroThrows() {
            assertThrows(ArithmeticException.class, () -> math.divide(10, 0),
                    "Should throw ArithmeticException when divisor is zero");
        }
    }

    // -----------------------------------------------------------------------
    // isEven()
    // -----------------------------------------------------------------------

    @Nested
    @DisplayName("isEven()")
    class IsEvenTests {

        @Test
        @DisplayName("even number returns true")
        void evenNumber() {
            assertTrue(math.isEven(8));
        }

        @Test
        @DisplayName("odd number returns false")
        void oddNumber() {
            assertFalse(math.isEven(7));
        }

        @Test
        @DisplayName("zero is even")
        void zero() {
            assertTrue(math.isEven(0));
        }
    }

    // -----------------------------------------------------------------------
    // factorial()
    // -----------------------------------------------------------------------

    @Nested
    @DisplayName("factorial()")
    class FactorialTests {

        @Test
        @DisplayName("factorial of 0 is 1")
        void factorialZero() {
            assertEquals(1L, math.factorial(0));
        }

        @Test
        @DisplayName("factorial of 6 is 720")
        void factorialSix() {
            assertEquals(720L, math.factorial(6));
        }

        @Test
        @DisplayName("throws IllegalArgumentException for negative input")
        void factorialNegativeThrows() {
            assertThrows(IllegalArgumentException.class, () -> math.factorial(-1));
        }
    }

    // -----------------------------------------------------------------------
    // isPrime()
    // -----------------------------------------------------------------------

    @Nested
    @DisplayName("isPrime()")
    class IsPrimeTests {

        @Test
        @DisplayName("13 is prime")
        void primeNumber() {
            assertTrue(math.isPrime(13));
        }

        @Test
        @DisplayName("1 is not prime")
        void one() {
            assertFalse(math.isPrime(1));
        }

        @Test
        @DisplayName("composite number 9 is not prime")
        void compositeNumber() {
            assertFalse(math.isPrime(9));
        }
    }
}
