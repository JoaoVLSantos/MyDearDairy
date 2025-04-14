package com.mydeardiary.service.user;

import com.mydeardiary.dto.user.AuthenticateUserDTO;
import com.mydeardiary.model.user.User;
import com.mydeardiary.repositories.user.UsersRepository;
import com.mydeardiary.service.JwtService;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticateUserServiceTest {

    private UsersRepository usersRepository;
    private AuthenticationConfiguration authenticationConfiguration;
    private JwtService jwtService;
    private AuthenticateUserService authenticateUserService;

    @BeforeEach
    void setUp() throws Exception {
        usersRepository = mock(UsersRepository.class);
        jwtService = mock(JwtService.class);
        authenticationConfiguration = mock(AuthenticationConfiguration.class);
        authenticateUserService = new AuthenticateUserService(usersRepository, authenticationConfiguration, jwtService);
    }

    @Test
    void testExecuteWithValidCredentialsReturnsToken() throws Exception {
        AuthenticateUserDTO dto = new AuthenticateUserDTO("teste@example.com", "senha123");
        AuthenticationManager authManager = mock(AuthenticationManager.class);
        Authentication auth = mock(Authentication.class);
        User user = new User();
        user.setEmail("teste@example.com");

        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authManager);
        when(authManager.authenticate(any())).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("fake-jwt-token");

        String token = authenticateUserService.execute(dto);
        assertEquals("fake-jwt-token", token);
    }

    @Test
    void testExecuteWithInvalidCredentialsThrowsException() throws Exception {
        AuthenticateUserDTO dto = new AuthenticateUserDTO("wrong@example.com", "wrongpass");
        AuthenticationManager authManager = mock(AuthenticationManager.class);

        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authManager);
        when(authManager.authenticate(any())).thenThrow(new RuntimeException());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authenticateUserService.execute(dto);
        });

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    }

    @Test
    void testLoadUserByUsernameWhenUserExists() {
        User user = new User();
        user.setEmail("test@example.com");
        when(usersRepository.findByEmail("test@example.com")).thenReturn(user);

        var result = authenticateUserService.loadUserByUsername("test@example.com");
        assertEquals(user, result);
    }

    @Test
    void testLoadUserByUsernameWhenUserDoesNotExistThrowsException() {
        when(usersRepository.findByEmail("missing@example.com")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            authenticateUserService.loadUserByUsername("missing@example.com");
        });
    }

    @Test
    void testDoFilterInternalWithValidToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer validtoken");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        when(jwtService.validateToken("validtoken")).thenReturn("test@example.com");

        User user = new User();
        user.setEmail("test@example.com");
        when(usersRepository.findByEmail("test@example.com")).thenReturn(user);

        authenticateUserService.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(user, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Test
    void testDoFilterInternalWithInvalidToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer invalidtoken");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        when(jwtService.validateToken("invalidtoken")).thenReturn(null);

        authenticateUserService.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternalWithoutAuthorizationHeader() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        authenticateUserService.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}