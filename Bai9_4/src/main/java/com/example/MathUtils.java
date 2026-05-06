package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MathUtils – utility class for common mathematical operations.
 */
public class MathUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(MathUtils.class);

    /**
     * Returns the sum of two integers.
     *
     * @param a first operand
     * @param b second operand
     * @return a + b
     */
    public int add(int a, int b) {
        int result = a + b;
        LOGGER.debug("add({}, {}) = {}", a, b, result);
        return result;
    }

    /**
     * Returns the difference of two integers.
     *
     * @param a first operand
     * @param b second operand
     * @return a - b
     */
    public int subtract(int a, int b) {
        int result = a - b;
        LOGGER.debug("subtract({}, {}) = {}", a, b, result);
        return result;
    }

    /**
     * Returns the product of two integers.
     *
     * @param a first operand
     * @param b second operand
     * @return a * b
     */
    public int multiply(int a, int b) {
        int result = a * b;
        LOGGER.debug("multiply({}, {}) = {}", a, b, result);
        return result;
    }

    /**
     * Returns the quotient of two doubles.
     *
     * @param a dividend
     * @param b divisor; must not be zero
     * @return a / b
     * @throws ArithmeticException if b is zero
     */
    public double divide(double a, double b) {
        if (b == 0) {
            LOGGER.error("Division by zero: {} / {}", a, b);
            throw new ArithmeticException("Cannot divide by zero");
        }
        double result = a / b;
        LOGGER.debug("divide({}, {}) = {}", a, b, result);
        return result;
    }

    /**
     * Returns true if the given integer is even.
     *
     * @param n any integer
     * @return true when n % 2 == 0
     */
    public boolean isEven(int n) {
        boolean result = (n % 2 == 0);
        LOGGER.debug("isEven({}) = {}", n, result);
        return result;
    }

    /**
     * Computes the factorial of a non-negative integer.
     *
     * @param n non-negative integer
     * @return n!
     * @throws IllegalArgumentException if n is negative
     */
    public long factorial(int n) {
        if (n < 0) {
            LOGGER.error("factorial() called with negative input: {}", n);
            throw new IllegalArgumentException("Factorial not defined for negative numbers");
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        LOGGER.debug("factorial({}) = {}", n, result);
        return result;
    }

    /**
     * Returns true if the given integer is a prime number.
     *
     * @param n the number to test
     * @return true when n is prime
     */
    public boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        LOGGER.debug("isPrime({}) = true", n);
        return true;
    }

    /**
     * Application entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        LOGGER.info("=== MathUtils Matrix Demo ===");
        MathUtils math = new MathUtils();
        LOGGER.info("3 + 5 = {}", math.add(3, 5));
        LOGGER.info("6! = {}", math.factorial(6));
        LOGGER.info("isPrime(13) = {}", math.isPrime(13));
        LOGGER.info("OS: {}", System.getProperty("os.name"));
        LOGGER.info("File.separator: '{}'", java.io.File.separator);
        LOGGER.info("=== Done ===");
    }
}
