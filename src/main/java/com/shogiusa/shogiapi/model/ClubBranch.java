package com.shogiusa.shogiapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity representing a Branch.
 * <p>
 * This entity encapsulates the details of a branch of a Shogi group. A branch typically
 * corresponds to a specific geographic location or city where Shogi players gather or
 * are affiliated. Examples of branches could include major cities like Los Angeles (LA),
 * San Francisco (SF), New York, etc.
 * <p>
 * Each branch is uniquely identified by an ID and is characterized by a name that
 * typically reflects its geographic location or a distinctive title representing the
 * Shogi community in that area.
 * <p>
 * The {@link Entity} annotation indicates that this class is a JPA entity, meaning it
 * represents a table in a database. The {@link Table} annotation specifies the actual
 * table name in the database, which is 'branch' in this case.
 * <p>
 * The {@link Data} annotation from Lombok library automatically generates boilerplate
 * code such as getters, setters, equals, hashCode, and toString methods, thus reducing
 * the verbosity of the Java code and enhancing readability and maintainability.
 */
@Data
@Entity
@Table(name="club_branch")
public class ClubBranch {

    /**
     * The unique identifier of the branch.
     * <p>
     * It's automatically generated and managed by the JPA provider (like Hibernate)
     * and is marked with {@link Id} and {@link GeneratedValue} annotations to indicate
     * its role as a primary key and its automatic generation strategy, respectively.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the branch.
     * <p>
     * This field represents the name of the branch, which often corresponds to the
     * location or a unique identifier of the Shogi group in a particular area.
     */
    private String name;
}
