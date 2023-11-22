package com.shogiusa.shogiapi.auditing;

import com.shogiusa.shogiapi.model.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

/**
 * This class implements the {@link AuditorAware} interface to provide auditing functionality within the application.
 * It is used to automatically populate the audit fields of entities (such as createdBy and lastModifiedBy) with the
 * identifier of the currently authenticated user.
 *
 * <p>ApplicationAuditAware integrates with Spring Security to access the authentication context. This allows the
 * application to capture the user information responsible for any create or update operations on the entities that
 * are being audited.</p>
 */
public class ApplicationAuditAware implements AuditorAware<Long> {

    /**
     * Retrieves the identifier (ID) of the currently authenticated user for auditing purposes.
     * This method is called by the Spring Data JPA auditing mechanism to get the ID of the user
     * responsible for the current database transaction.
     *
     * <p>If the current security context has an authenticated user, their ID is returned.
     * Otherwise, if there is no authenticated user (for example, if the operation is anonymous),
     * the method returns an empty {@link Optional}.</p>
     *
     * @return An {@link Optional} containing the ID of the authenticated user, or an empty {@link Optional}
     *         if no user is authenticated.
     */
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken
        ) {
            return Optional.empty();
        }

        User userPrincipal = (User) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getId());
    }
}