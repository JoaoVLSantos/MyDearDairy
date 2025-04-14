package com.mydeardiary.service.user;

import com.mydeardiary.dto.user.RegisterUserDTO;
import com.mydeardiary.model.user.Role;
import com.mydeardiary.model.user.User;
import com.mydeardiary.repositories.user.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class RegisterUserService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegisterUserService(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(RegisterUserDTO dto) {
        if (usersRepository.findByEmail(dto.email()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um usuário cadastrado com este e-mail.");
        }

        var user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.USER);

        usersRepository.save(user);
    }
}