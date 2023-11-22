package com.shogiusa.shogiapi.repository;

import com.shogiusa.shogiapi.model.PlayerShogilLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link PlayerShogilLevel} entity.
 * <p>
 * This interface serves as a data access layer for Level entities, providing a range of standard
 * methods for interacting with the database. It extends {@link JpaRepository} and {@link JpaSpecificationExecutor},
 * thus inheriting common JPA repository operations and the ability to execute specification-based queries.
 * <p>
 * The {@link JpaRepository} extension brings in standard CRUD operations like findAll(), save(), findById(),
 * delete(), and more, simplifying interactions with the database. The {@link JpaSpecificationExecutor} extension
 * allows for the execution of complex, specification-based queries, offering flexibility in retrieving Level entities
 * based on various criteria.
 */
@Repository
public interface PlayerShogilLevelRepository extends JpaRepository<PlayerShogilLevel, Long>, JpaSpecificationExecutor<PlayerShogilLevel> {
    // Additional custom repository methods can be added here if needed.
    // The interface already offers a wide array of common JPA repository operations.
}
