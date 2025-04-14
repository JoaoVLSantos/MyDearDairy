package com.mydeardiary.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydeardiary.dto.user.AuthenticateUserDTO;
import com.mydeardiary.dto.user.RegisterUserDTO;
import com.mydeardiary.service.user.AuthenticateUserService;
import com.mydeardiary.service.user.RegisterUserService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterUserService registerUserService;

    @MockBean
    private AuthenticateUserService authenticateUserService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterUserReturnsCreated() throws Exception {
        RegisterUserDTO dto = new RegisterUserDTO("João", "joao@example.com", "senha123");

        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testLoginReturnsToken() throws Exception {
        AuthenticateUserDTO dto = new AuthenticateUserDTO("joao@example.com", "senha123");
        String fakeToken = "fake.jwt.token";

        Mockito.when(authenticateUserService.execute(dto)).thenReturn(fakeToken);

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string(fakeToken));
    }

    @Test
    void testLoginInvalidDataReturnsBadRequest() throws Exception {
        AuthenticateUserDTO dto = new AuthenticateUserDTO("", ""); // dados inválidos

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}