package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/** Unit tests for {@link MathUtils}. */
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
    }

    @Nested
    @DisplayName("subtract()")
    class SubtractTests {

        @Test
        void subtractNormal() {
            assertEquals(6, math.subtract(10, 4));
        }
    }

    @Nested
    @DisplayName("multiply()")
    class MultiplyTests {

        @Test
        void multiplyPositive() {
            assertEquals(42, math.multiply(6, 7));
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
        void divideByZeroThrows() {
            assertThrows(ArithmeticException.class, () -> math.divide(10, 0));
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
}
