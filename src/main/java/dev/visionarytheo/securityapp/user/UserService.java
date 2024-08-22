package dev.visionarytheo.securityapp.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo repo;

    public UserService(UserRepo repo) {
        this.repo = repo;
    }

    public List<AppUser> getUsers() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            return null;
        }

        Role role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(Role::valueOf)
                .findFirst()
                .orElse(null);

        assert role != null;

        if (!role.equals(Role.ADMIN)) {
            return null;
        }

        return repo.findAll();
    }
}
