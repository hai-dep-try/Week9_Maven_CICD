package com.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Calculates shipping costs based on weight and shipping type.
 */
public class ShippingCalculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingCalculator.class);

    /**
     * Calculates the shipping cost.
     *
     * @param weight package weight in kg; must be positive
     * @param type   shipping type: "EXPRESS" or "STANDARD"
     * @return shipping cost in VND
     * @throws IllegalArgumentException if weight <= 0, type is null, or type is unknown
     */
    public double calculate(double weight, String type) {
        if (weight <= 0) {
            LOGGER.warn("Rejected: weight={} is non-positive", weight);
            throw new IllegalArgumentException("Weight must be positive");
        }
        // Bug 4 FIX: null check before .equals() to avoid NullPointerException
        if (type == null) {
            LOGGER.error("Rejected: shipping type is null");
            throw new IllegalArgumentException("Shipping type must not be null");
        }
        if (type.equals("EXPRESS")) {
            double cost = weight * 5000 + 20000;
            LOGGER.info("EXPRESS: weight={}, cost={}", weight, cost);
            return cost;
        }
        if (type.equals("STANDARD")) {
            double cost = weight * 3000;
            LOGGER.info("STANDARD: weight={}, cost={}", weight, cost);
            return cost;
        }
        LOGGER.error("Unknown type: {}", type);
        throw new IllegalArgumentException("Unknown type: " + type);
    }
}
