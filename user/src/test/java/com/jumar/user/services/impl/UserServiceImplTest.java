package com.jumar.user.services.impl;

import com.jumar.user.dto.CreateUserDto;
import com.jumar.user.exceptions.OneOrMoreUserRequiredFieldsNullException;
import com.jumar.user.models.User;
import com.jumar.user.repository.AddressRepository;
import com.jumar.user.repository.UserRepository;
import com.jumar.user.services.UserService;
import com.jumar.user.utils.PasswordUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    AddressRepository addressRepository;

    private UserService userService;
    private CreateUserDto createUserDtoTest;
    private User userTest;
    private User createdUser;

    @BeforeEach
    void setup() {
        userService = new UserServiceImpl(userRepository, addressRepository);
        createTestDto();
        userTest = createTestUser();
        createdUser = userService.createUser(createUserDtoTest);
    }

    @Test
    void should_returnTrue_when_comparingReturnedUser_to_TestUser(){

        assertThat(userTest).usingRecursiveComparison().ignoringFields("id",
                "username",
                "dateAdded",
                "dateLastModified",
                "failedLoginAttempts",
                "isDeleted").isEqualTo(userService.createUser(createUserDtoTest));
    }

    @Test
    void should_throwException_when_anyFields_areNull(){
        assertThatThrownBy(() -> {
            userService.createUser(new CreateUserDto());
        }).isInstanceOf(OneOrMoreUserRequiredFieldsNullException.class).hasMessageContaining("One or more fields were empty.");
    }

    @Test
    void should_returnTrue_when_comparingDateAdded_to_dateLastModified() {
        assertThat(createdUser.getDateAdded()).isEqualTo(createdUser.getDateLastModified());
    }

    @Test
    void should_returnFalse_when_checking_dateAddedAndDateLastModified_isNull() {
        assertThat(createdUser.getDateLastModified()).isNotNull();
        assertThat(createdUser.getDateAdded()).isNotNull();
    }

    @Test
    void should_returnTrue_when_checking_FailedLoginAttempts_isZero() {
        assertThat(createdUser.getFailedLoginAttempts()).isEqualTo(0).isNotNull();
    }

    @Test
    void should_returnTrue_when_checking_IsDeleted_isFalse() {
        assertThat(createdUser.isDeleted()).isFalse();
    }

    @Test
    void should_returnTrue_when_comparing_emailAddress_to_username(){
        assertThat(createdUser.getUsername()).isNotNull().isEqualTo(createdUser.getEmailAddress());
    }

    @Test
    void should_returnFalse_when_comparing_plaintextPassword_to_hashedPasswordProperty() {
        String plaintextPass = "plaintext";
        createUserDtoTest.setPasswordHash(plaintextPass);
        User passwordTest = userService.createUser(createUserDtoTest);
        assertThat(passwordTest).extracting("passwordHash").isNotEqualTo(plaintextPass);
    }
    private void createTestDto() {
        createUserDtoTest = new CreateUserDto();
        createUserDtoTest.setFirstName("firstName");
        createUserDtoTest.setLastName("lastName");
        createUserDtoTest.setEmailAddress("testEmail@email.com");
        createUserDtoTest.setTelephone("0121123456");
        createUserDtoTest.setDateOfBirth(LocalDateTime.of(1994, 4, 2, 0, 0));
        try {
            createUserDtoTest.setPasswordHash("test");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private User createTestUser() {
        User userTestReturn = new User();
        userTestReturn.setId(1);
        userTestReturn.setFirstName("firstName");
        userTestReturn.setLastName("lastName");
        userTestReturn.setEmailAddress("testEmail@email.com");
        userTestReturn.setTelephone("0121123456");
        userTestReturn.setDateOfBirth(LocalDateTime.of(1994, 4, 2, 0, 0));
        userTestReturn.setUsername("testEmail@email.com");
        try {
            userTestReturn.setPasswordHash(PasswordUtils.hashPassword("test"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        userTestReturn.setDateAdded(LocalDateTime.of(2023, 5, 12, 0,0));
        userTestReturn.setDateLastModified(LocalDateTime.of(2023, 5, 12, 0,0));
        userTestReturn.setFailedLoginAttempts(0);
        userTestReturn.setDeleted(false);

        return userTestReturn;
    }


}