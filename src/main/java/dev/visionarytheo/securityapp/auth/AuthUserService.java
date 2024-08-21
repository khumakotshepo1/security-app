package dev.visionarytheo.securityapp.auth;

import dev.visionarytheo.securityapp.user.AppUser;
import dev.visionarytheo.securityapp.user.UserRepo;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService {

    private final UserRepo repo;
    private final AuthenticationProvider authProvider;

    public AuthUserService(UserRepo repo, AuthenticationProvider authProvider) {
        this.repo = repo;
        this.authProvider = authProvider;
    }


    public String register(AppUser user) {


        if (repo.findByEmail(user.getEmail()) != null) {
            return "User already exists";
        }


        user.setPassword(new BCryptPasswordEncoder(10).encode(user.getPassword()));

        repo.save(user);
        return "User registered successfully";
    }

    public String verify(AppUser user) {

        Authentication auth =
                authProvider.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        if (!auth.isAuthenticated()) {
            return "User not found or password is incorrect";
        }

        return "User logged in successfully";
    }
}
