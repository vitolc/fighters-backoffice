package com.vitulc.fightersapi.app.services;


import com.vitulc.fightersapi.app.dtos.LoginRequestDto;
import com.vitulc.fightersapi.app.dtos.UserDto;
import com.vitulc.fightersapi.app.entities.Users;
import com.vitulc.fightersapi.app.errors.exceptions.BadRequestException;
import com.vitulc.fightersapi.app.errors.exceptions.ConflictException;
import com.vitulc.fightersapi.app.errors.exceptions.ForbiddenException;
import com.vitulc.fightersapi.app.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public AuthenticationService(
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            UserRepository userRepository) {

        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> register(UserDto userDto) {

        if (userRepository.existsByUsername(userDto.username())){
            throw new ConflictException("Username already used");
        }

        if (userRepository.existsByEmail(userDto.email())){
            throw new ConflictException("Email already used");
        }

        if(!userDto.password().equals(userDto.confirmPassword())){
            throw new BadRequestException("Password and confirmation do not match");
        }

        var encryptedPassword = passwordEncoder.encode(userDto.password());
        var user = new Users(userDto, encryptedPassword);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    public ResponseEntity<String> login(LoginRequestDto loginRequestDto) {

        try {
            Authentication authenticationRequest =
                    UsernamePasswordAuthenticationToken.unauthenticated(
                            loginRequestDto.email(), loginRequestDto.password());

            Authentication authenticationResponse =
                    this.authenticationManager.authenticate(authenticationRequest);

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authenticationResponse);
            SecurityContextHolder.setContext(securityContext);

            return ResponseEntity.ok().body("Login successfully");
        }catch(AuthenticationException exc){
            throw new ForbiddenException("Invalid email or password");
        }
    }

    public Users getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
