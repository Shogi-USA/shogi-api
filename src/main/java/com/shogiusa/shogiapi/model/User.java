package com.shogiusa.shogiapi.model;

import com.shogiusa.shogiapi.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

/**
 * Represents a user in the system.
 * <p>
 * This class is a JPA entity that maps to the 'user' table in the database. It also implements {@link UserDetails}
 * to be used with Spring Security for authentication and authorization purposes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")
public class User implements UserDetails {

    private static final Logger LOGGER = LoggerFactory.getLogger(User.class);

    /**
     * The unique identifier of the user.
     * <p>
     * It is automatically generated using the {@link GenerationType#IDENTITY} strategy,
     * relying on the underlying database for generating a unique key for each new record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the user.
     * <p>
     * This field is unique and typically used for user identification in the system.
     */
    @Column(unique = true)
    private String username;

    /**
     * The email address of the user.
     * <p>
     * This field is unique and can be used for user identification and communication.
     */
    @Column(unique = true)
    private String email;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The display name of the user.
     * <p>
     * This field is used to represent the user in a more friendly or informal way, different from their username.
     */
    private String displayName;

    /**
     * The encrypted password of the user.
     */
    private String password;

    /**
     * The category associated with the user.
     * <p>
     * Represents a many-to-one relationship to the {@link AgeCategory} entity.
     */
    @ManyToOne
    @JoinColumn(name = "age_category_id") // This is the column in the 'user' table that joins to the 'category' table
    private AgeCategory ageCategory;

    /**
     * The branch associated with the user.
     * <p>
     * Represents a many-to-one relationship to the {@link ClubBranch} entity.
     */
    @ManyToOne
    @JoinColumn(name = "club_branch_id") // This is the column in the 'user' table that joins to the 'branch' table
    private ClubBranch clubBranch;

    /**
     * The level associated with the user.
     * <p>
     * Represents a many-to-one relationship to the {@link PlayerShogilLevel} entity.
     */
    @ManyToOne
    @JoinColumn(name = "player_shogi_level_id") // This is the column in the 'user' table that joins to the 'level' table
    private PlayerShogilLevel playerShogilLevel;

    /**
     * The role assigned to the user.
     * <p>
     * This field uses an enumeration {@link Role} to specify the user's role in the system.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * The list of tokens associated with the user.
     * <p>
     * Represents a one-to-many relationship to the {@link Token} entity, indicating all tokens issued to the user.
     */
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    /**
     * Returns the user's authorities, which are derived from their role.
     * <p>
     * This method is part of the {@link UserDetails} interface and is used by Spring Security
     * to determine the user's authorities for authorization purposes.
     *
     * @return Collection of {@link GrantedAuthority} representing the user's authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Return actual username
     * @return
     */
    public String getActualUsername() {
        return username;
    }

    /**
     * This method is used by JwtAuthenticationFilter
     * email is used to extract credentials from the token
     * TODO: configure JwtAuthenticationFilter to use username instead of email
     * @return
     */
    @Override
    public String getUsername() {
        LOGGER.info("username: {}", username);
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
