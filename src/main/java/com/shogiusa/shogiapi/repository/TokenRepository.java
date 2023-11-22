package com.shogiusa.shogiapi.repository;

import java.util.List;
import java.util.Optional;
import com.shogiusa.shogiapi.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Token} entity.
 * <p>
 * This interface provides the data access layer for Token entities, enabling various operations
 * for interacting with the database related to authentication tokens. It extends {@link JpaRepository},
 * thereby inheriting a collection of standard JPA repository operations.
 * <p>
 * The {@link JpaRepository} extension provides common JPA repository operations like findAll(),
 * save(), findById(), delete(), and others, enabling straightforward interactions with the
 * database for Token entities.
 * <p>
 * Additional methods are defined to cater to specific token-related operations:
 * - {@link #findAllValidTokenByUser(Long)}: Retrieves a list of valid (neither expired nor revoked)
 *   tokens associated with a specific user.
 * - {@link #findByToken(String)}: Finds a specific token by its string value.
 * <p>
 * These methods enhance the standard functionalities provided by JpaRepository, making the
 * repository tailored to the specific needs of handling authentication tokens within the application.
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    /**
     * Retrieves all valid tokens (neither expired nor revoked) for a given user.
     *
     * @param id The user's ID for whom the tokens are to be retrieved.
     * @return A list of {@link Token} objects that are valid for the specified user.
     */
    @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Long id);

    /**
     * Finds a token entity based on its token string.
     *
     * @param token The string representation of the token.
     * @return An {@link Optional} containing the {@link Token} if found, or empty if not.
     */
    Optional<Token> findByToken(String token);
}