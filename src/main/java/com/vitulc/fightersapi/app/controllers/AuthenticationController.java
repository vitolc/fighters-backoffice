package com.vitulc.fightersapi.app.controllers;


import com.vitulc.fightersapi.app.services.AuthenticationService;
import com.vitulc.fightersapi.app.dtos.LoginRequestDto;
import com.vitulc.fightersapi.app.dtos.UserDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserDto userDto) {
        return authenticationService.register(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return authenticationService.login(loginRequestDto);
    }

}
