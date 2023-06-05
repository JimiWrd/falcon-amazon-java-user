package com.jumar.user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumar.user.dto.CreateUserDto;
import com.jumar.user.fixtures.UserFixtures;
import com.jumar.user.models.User;
import com.jumar.user.repository.UserRepository;
import com.jumar.user.services.UserService;
import lombok.SneakyThrows;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    UserService userService;

    private CreateUserDto createUserDtoTest;

    @BeforeEach
    void setup() {
        createUserDtoTest = UserFixtures.generateCreateUserDto();
    }

    @SneakyThrows
    @Test
    void should_returnCreated_when_callingCreateUserEndpoint() {
        User newUser = UserFixtures.generateValidUser();
        when(userService.createUser(any(CreateUserDto.class))).thenReturn(newUser);

        mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDtoTest)))
                .andExpect(status().isCreated());
    }

    @SneakyThrows
    @Test
    void should_returnBadRequest_when_callingCreateUser_with_invalidEmail() {
        createUserDtoTest.setEmailAddress("gibberish");
        mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDtoTest)))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void should_returnBadRequest_when_ForenamesIsNull() {
        createUserDtoTest.setForenames(null);
        mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDtoTest)))
                .andExpect(status().isBadRequest());
    }
}