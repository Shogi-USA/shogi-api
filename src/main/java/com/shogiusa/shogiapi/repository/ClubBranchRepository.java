package com.shogiusa.shogiapi.repository;

import com.shogiusa.shogiapi.model.ClubBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link ClubBranch} entity.
 * <p>
 * This interface is used for data access operations on Branch entities in the database. It extends
 * {@link JpaRepository} and {@link JpaSpecificationExecutor}, thereby inheriting several methods
 * for CRUD operations and allowing the execution of specification-based queries.
 */
@Repository
public interface ClubBranchRepository extends JpaRepository<ClubBranch, Long>, JpaSpecificationExecutor<ClubBranch> {
    // This interface can have custom methods if needed, but by default,
    // it inherits a lot of common JPA repository operations from JpaRepository
    // and JpaSpecificationExecutor, such as findAll(), save(), findById(), delete(), etc.
}
