package com.mydeardiary.service.user;

import com.mydeardiary.dto.user.RegisterUserDTO;
import com.mydeardiary.model.user.Role;
import com.mydeardiary.model.user.User;
import com.mydeardiary.repositories.user.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterUserServiceTest {

    private UsersRepository usersRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private RegisterUserService registerUserService;

    @BeforeEach
    void setUp() {
        usersRepository = mock(UsersRepository.class);
        passwordEncoder = new BCryptPasswordEncoder();
        registerUserService = new RegisterUserService(usersRepository, passwordEncoder);
    }

    @Test
    void testRegisterNewUserSuccessfully() {
        RegisterUserDTO dto = new RegisterUserDTO("Maria", "maria@example.com", "123456");

        when(usersRepository.findByEmail(dto.email())).thenReturn(null); // nenhum usuário com o mesmo e-mail

        registerUserService.execute(dto);

        verify(usersRepository, times(1)).save(argThat(user -> 
            user.getName().equals("Maria") &&
            user.getEmail().equals("maria@example.com") &&
            passwordEncoder.matches("123456", user.getPassword()) &&
            user.getRole() == Role.USER
        ));
    }

    @Test
    void testRegisterUserWithExistingEmailThrowsException() {
        RegisterUserDTO dto = new RegisterUserDTO("João", "joao@example.com", "senha123");
        when(usersRepository.findByEmail(dto.email())).thenReturn(new User());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            registerUserService.execute(dto);
        });

        assertEquals("409 CONFLICT \"Já existe um usuário cadastrado com este e-mail.\"", exception.getMessage());

        verify(usersRepository, never()).save(any());
    }
}
