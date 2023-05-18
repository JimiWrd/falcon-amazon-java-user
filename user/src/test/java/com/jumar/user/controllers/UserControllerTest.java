package com.jumar.user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumar.user.dto.CreateUserDto;
import com.jumar.user.services.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@ExtendWith(MockitoExtension.class)
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
        generateCreateUserDto();
    }

    @SneakyThrows
    @Test
    void should_returnCreated_when_callingCreateUserEndpoint() {
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

    private void generateCreateUserDto() {
        createUserDtoTest = new CreateUserDto();
        createUserDtoTest.setForenames("firstName");
        createUserDtoTest.setSurname("lastName");
        createUserDtoTest.setEmailAddress("testEmail@email.com");
        createUserDtoTest.setTelephone("0121123456");
        createUserDtoTest.setDateOfBirth(LocalDate.of(1994, 4, 2));
        try {
            createUserDtoTest.setPasswordHash("test");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}