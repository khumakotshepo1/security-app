package dev.visionarytheo.securityapp.auth;

import dev.visionarytheo.securityapp.user.AppUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthUserService service;

    public AuthController(AuthUserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    String register(@RequestBody AppUser user) {
        return service.register(user);
    }

    @PostMapping("/login")
    String login(@RequestBody AppUser user) {
        return service.verify(user);
    }
}
