package com.lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ShippingCalculator}.
 */
@DisplayName("ShippingCalculator Test Suite")
public class ShippingCalculatorTest {

    private ShippingCalculator calc;

    /** Creates a fresh calculator before each test. */
    @BeforeEach
    void setUp() {
        calc = new ShippingCalculator();
    }

    @Test
    @DisplayName("STANDARD: 5kg -> 15000")
    void testStandard() {
        assertEquals(15000.0, calc.calculate(5, "STANDARD"));
    }

    @Test
    @DisplayName("EXPRESS: 5kg -> 45000  (5*5000 + 20000)")
    void testExpress() {
        assertEquals(45000.0, calc.calculate(5, "EXPRESS"));
    }

    @Test
    @DisplayName("Negative weight throws IllegalArgumentException")
    void testInvalidWeight() {
        assertThrows(IllegalArgumentException.class,
            () -> calc.calculate(-1, "STANDARD"));
    }

    // ── Bug 4: null type test ────────────────────────────────────────────────
    // BEFORE FIX: calc.calculate(5, null) throws NullPointerException
    //             because type.equals("EXPRESS") blows up on null receiver.
    // AFTER FIX:  explicit null check → throws IllegalArgumentException cleanly.
    @Test
    @DisplayName("Bug 4 – null type throws IllegalArgumentException (not NPE)")
    void testNullType() {
        assertThrows(IllegalArgumentException.class,
            () -> calc.calculate(5, null));
    }

    @Test
    @DisplayName("Unknown type throws IllegalArgumentException")
    void testUnknownType() {
        assertThrows(IllegalArgumentException.class,
            () -> calc.calculate(5, "OVERNIGHT"));
    }
}
