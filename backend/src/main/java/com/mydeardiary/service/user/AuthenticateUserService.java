package com.mydeardiary.service.user;

import com.mydeardiary.dto.user.AuthenticateUserDTO;
import com.mydeardiary.model.user.User;
import com.mydeardiary.repositories.user.UsersRepository;
import com.mydeardiary.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
public class AuthenticateUserService extends OncePerRequestFilter implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtService jwtService;

    public AuthenticateUserService(UsersRepository usersRepository,
                                    AuthenticationConfiguration authenticationConfiguration,
                                    JwtService jwtService) {
        this.usersRepository = usersRepository;
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtService = jwtService;
    }

    public String execute(AuthenticateUserDTO data) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var authenticationManager = authenticationConfiguration.getAuthenticationManager();
            var auth = authenticationManager.authenticate(authToken);
            return jwtService.generateToken((User) auth.getPrincipal());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos");
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getToken(request);
        if (token != null && !token.isBlank()) {
            String email = jwtService.validateToken(token);
            if (email != null && !email.isBlank()) {
                UserDetails user = usersRepository.findByEmail(email);
                if (user != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = usersRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com email: " + username);
        }
        return user;
    }

    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }
        return authorization.replace("Bearer ", "");
    }
}