package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MathUtils – core utility class.
 *
 * <p>All console output has been replaced with structured logging via SLF4J +
 * Logback so that log level, format, and destination can be controlled without
 * recompiling.</p>
 */
public class MathUtils {

    // SLF4J logger – replaces raw System.out.println calls
    private static final Logger logger = LoggerFactory.getLogger(MathUtils.class);

    // -----------------------------------------------------------------------
    // Basic arithmetic
    // -----------------------------------------------------------------------

    /**
     * Returns the sum of two integers.
     */
    public int add(int a, int b) {
        int result = a + b;
        logger.debug("add({}, {}) = {}", a, b, result);
        return result;
    }

    /**
     * Returns the difference of two integers.
     */
    public int subtract(int a, int b) {
        int result = a - b;
        logger.debug("subtract({}, {}) = {}", a, b, result);
        return result;
    }

    /**
     * Returns the product of two integers.
     */
    public int multiply(int a, int b) {
        int result = a * b;
        logger.debug("multiply({}, {}) = {}", a, b, result);
        return result;
    }

    /**
     * Returns the quotient of two doubles.
     *
     * @throws ArithmeticException if {@code b} is zero
     */
    public double divide(double a, double b) {
        if (b == 0) {
            logger.error("Division by zero attempted: {} / {}", a, b);
            throw new ArithmeticException("Cannot divide by zero");
        }
        double result = a / b;
        logger.debug("divide({}, {}) = {}", a, b, result);
        return result;
    }

    // -----------------------------------------------------------------------
    // Number theory helpers
    // -----------------------------------------------------------------------

    /**
     * Checks whether a number is even.
     */
    public boolean isEven(int n) {
        boolean result = (n % 2 == 0);
        logger.debug("isEven({}) = {}", n, result);
        return result;
    }

    /**
     * Computes the factorial of a non-negative integer.
     *
     * @throws IllegalArgumentException for negative input
     */
    public long factorial(int n) {
        if (n < 0) {
            logger.error("factorial() called with negative input: {}", n);
            throw new IllegalArgumentException("Factorial not defined for negative numbers");
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        logger.debug("factorial({}) = {}", n, result);
        return result;
    }

    /**
     * Checks whether a number is prime.
     */
    public boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        logger.debug("isPrime({}) = {}", n, n >= 2);
        return true;
    }

    // -----------------------------------------------------------------------
    // Entry point (demonstration)
    // -----------------------------------------------------------------------

    public static void main(String[] args) {
        logger.info("=== MathUtils Demo ===");

        MathUtils math = new MathUtils();

        logger.info("3 + 5 = {}", math.add(3, 5));
        logger.info("10 - 4 = {}", math.subtract(10, 4));
        logger.info("6 * 7 = {}", math.multiply(6, 7));
        logger.info("15 / 4 = {}", math.divide(15, 4));
        logger.info("isEven(8) = {}", math.isEven(8));
        logger.info("factorial(6) = {}", math.factorial(6));
        logger.info("isPrime(13) = {}", math.isPrime(13));

        logger.info("=== Demo complete ===");
    }
}
