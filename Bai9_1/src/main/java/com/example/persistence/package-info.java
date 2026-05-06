/**
 * Persistence layer – Hibernate Core 6.2.0.Final is configured as a dependency
 * so that JPA entity classes and SessionFactory wiring can be added here in
 * future sprints without any pom.xml changes.
 *
 * <p>Example stub (not yet active):
 * <pre>
 * {@code
 * @Entity
 * @Table(name = "calculations")
 * public class CalculationRecord {
 *     @Id @GeneratedValue
 *     private Long id;
 *     private String operation;
 *     private double result;
 * }
 * }
 * </pre>
 * </p>
 */
package com.example.persistence;
