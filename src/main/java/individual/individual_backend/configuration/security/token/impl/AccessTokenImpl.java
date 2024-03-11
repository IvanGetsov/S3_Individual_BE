package individual.individual_backend.configuration.security.token.impl;

import individual.individual_backend.configuration.security.token.AccessToken;
import individual.individual_backend.domain.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
@Getter
@EqualsAndHashCode
public class AccessTokenImpl implements AccessToken {
    private final String subject;
    private final Integer userId;
    private final String role;

    public AccessTokenImpl(String subject, Integer userId, String role) {
        this.subject = subject;
        this.userId = userId;
        this.role = role;
    }

    @Override
    public boolean hasRole(String roleName) {
        return this.role.equals(roleName);
    }
}
