package com.shogiusa.shogiapi.model;

import com.shogiusa.shogiapi.enums.TokenType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a Token in the application.
 * <p>
 * This class is a JPA entity used to map the 'token' table in the database. It includes properties
 * for token management such as token value, type, revocation status, and expiration status, along
 * with an association to a {@link User}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="token")
public class Token {

    /**
     * The unique identifier of the token.
     * <p>
     * It is automatically generated using the {@link GenerationType#IDENTITY} strategy,
     * relying on the underlying database for generating a unique key for each new record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    /**
     * The value of the token.
     * <p>
     * This is a unique field representing the actual token string used for authentication or other purposes.
     */
    @Column(unique = true)
    public String token;

    /**
     * The type of the token.
     * <p>
     * This field uses an enumeration {@link TokenType} to specify the type of token, e.g., BEARER.
     */
    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    /**
     * Flag indicating whether the token has been revoked.
     * <p>
     * A value of 'true' indicates that the token is no longer valid and should not be used.
     */
    public boolean revoked;

    /**
     * Flag indicating whether the token has expired.
     * <p>
     * A value of 'true' means that the token has passed its expiration time and is no longer valid.
     */
    public boolean expired;

    /**
     * The user associated with the token.
     * <p>
     * This field represents a many-to-one relationship to the {@link User} entity, indicating the user to whom
     * this token is issued. It is fetched lazily to optimize performance.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
}