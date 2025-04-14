package com.mydeardiary.service;

import com.mydeardiary.model.user.Role;
import com.mydeardiary.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    private final String jwtSecret = "test-secret";
    private final String gmtOffset = "-03:00";

    private User user;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        // Setando os valores @Value manualmente
        ReflectionTestUtils.setField(jwtService, "jwtSecret", jwtSecret);
        ReflectionTestUtils.setField(jwtService, "gmtOffset", gmtOffset);

        user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setRole(Role.USER);
    }

    @Test
    void testGenerateAndValidateTokenSuccessfully() {
        String token = jwtService.generateToken(user);
        assertNotNull(token);

        String subject = jwtService.validateToken(token);
        assertEquals(user.getEmail(), subject);
    }

    @Test
    void testValidateInvalidToken() {
        String invalidToken = "this.is.an.invalid.token";
        String result = jwtService.validateToken(invalidToken);
        assertNull(result);
    }

    @Test
    void testGenerateTokenWithInvalidSecretThrowsException() {
        ReflectionTestUtils.setField(jwtService, "jwtSecret", ""); // secret inválido

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            jwtService.generateToken(user);
        });

        assertEquals("500 INTERNAL_SERVER_ERROR \"JWT secret está inválido (null ou vazio)\"", exception.getMessage());
    }
}
