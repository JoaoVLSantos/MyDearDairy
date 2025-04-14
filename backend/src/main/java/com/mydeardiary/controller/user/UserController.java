package com.mydeardiary.controller.user;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mydeardiary.dto.user.AuthenticateUserDTO;
import com.mydeardiary.dto.user.RegisterUserDTO;
import com.mydeardiary.service.user.AuthenticateUserService;
import com.mydeardiary.service.user.RegisterUserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final RegisterUserService registerUserService;
    private final AuthenticateUserService authenticateUserService;

    public UserController(RegisterUserService registerUserService,
                          AuthenticateUserService authenticateUserService) {
        this.registerUserService = registerUserService;
        this.authenticateUserService = authenticateUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterUserDTO dto) {
        registerUserService.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthenticateUserDTO dto) {
        String token = authenticateUserService.execute(dto);
        return ResponseEntity.ok(token);
    }
}