package com.shogiusa.shogiapi.repository;

import com.shogiusa.shogiapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository interface for {@link User} entity.
 * <p>
 * This interface serves as a mechanism for encapsulating storage, retrieval,
 * and search behavior for User entities. It extends the {@link JpaRepository},
 * which provides standard JPA functionalities for basic CRUD (Create, Read, Update,
 * Delete) operations, as well as pagination and sorting capabilities.
 * <p>
 * Additionally, it extends {@link JpaSpecificationExecutor}, allowing the execution
 * of complex queries on User entities in a type-safe manner. This feature is
 * particularly useful for dynamic query construction based on varying criteria.
 * <p>
 * The repository also includes custom methods such as {@code findByEmail} and
 * {@code findByUsername}, which provide convenient ways to retrieve users based
 * on their unique email or username. The use of {@link Optional} in these methods
 * ensures a more robust and null-safe way of handling User retrieval, thus enhancing
 * the overall reliability of data access operations.
 * <p>
 * This repository plays a key role in the data layer of the application, especially
 * in managing user-related data operations and serving as a bridge between the
 * application and the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * Find a user by their email.
     *
     * @param email The email of the user to be found.
     * @return An Optional containing the user if found, or an empty Optional otherwise.
     */
    Optional<User> findByEmail(String email);

    /**
     * Find a user by their username.
     *
     * @param username The username of the user to be found.
     * @return An Optional containing the user if found, or an empty Optional otherwise.
     */
    Optional<User> findByUsername(String username);
}
