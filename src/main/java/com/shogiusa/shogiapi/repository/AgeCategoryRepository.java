package com.shogiusa.shogiapi.repository;

import com.shogiusa.shogiapi.model.AgeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link AgeCategory} entity.
 * <p>
 * This interface facilitates data access operations for Category entities in the database. It extends
 * {@link JpaRepository} and {@link JpaSpecificationExecutor}, providing a range of standard methods
 * for CRUD operations and enabling the execution of specification-based queries.
 * <p>
 * By extending {@link JpaRepository}, it inherits methods like findAll(), save(), findById(), delete(),
 * and many others that provide convenient ways to interact with the database. The {@link JpaSpecificationExecutor}
 * allows for more complex queries based on specified criteria.
 */
@Repository
public interface AgeCategoryRepository extends JpaRepository<AgeCategory, Long>, JpaSpecificationExecutor<AgeCategory> {
    // Custom repository methods can be defined here if needed.
    // Otherwise, the interface already provides a wide array of common JPA repository operations.
}
