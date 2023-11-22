package com.shogiusa.shogiapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity representing a Category.
 * <p>
 * This entity is used to categorize Shogi players into different segments, typically based on
 * age or skill level. Common categories include "Adult" and "Child," which help in organizing
 * events, tournaments, or matches that are appropriate for different groups of players.
 * <p>
 * The {@link Entity} annotation indicates that this class is a JPA entity, representing a
 * table in a database. The {@link Table} annotation specifies the actual table name in
 * the database, which is 'category' in this case.
 * <p>
 * The {@link Data} annotation from the Lombok library automatically generates boilerplate
 * code such as getters, setters, equals, hashCode, and toString methods, reducing the
 * verbosity of Java code and enhancing readability and maintainability.
 */
@Data
@Entity
@Table(name = "age_category")
public class AgeCategory {

    /**
     * The unique identifier of the category.
     * <p>
     * It is automatically generated and managed by the JPA provider (like Hibernate)
     * and is marked with {@link Id} and {@link GeneratedValue} annotations to indicate
     * its role as a primary key and its automatic generation strategy, respectively.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the category.
     * <p>
     * This field represents the specific category designation, such as "Adult" or "Child."
     * These categories are used to differentiate players in various contexts, including
     * tournaments, training programs, and Shogi community events.
     */
    private String name;
}