package com.jumar.user.services.impl;

import com.jumar.user.dto.CreateUserDto;
import com.jumar.user.dto.UpdateUserDto;
import com.jumar.user.exceptions.UserNotFoundException;
import com.jumar.user.exceptions.UsernameAlreadyExistsException;
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
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    AddressRepository addressRepository;

    private UserService userService;
    private CreateUserDto createUserDto;
    private User testUser;
    private User createdUser;

    @BeforeEach
    void setup() {
        userService = new UserServiceImpl(userRepository, addressRepository);
        createTestDto();
        testUser = createTestUser();
        createdUser = userService.createUser(createUserDto);
    }

    @Test
    void should_returnTrue_when_comparingReturnedUser_to_TestUser(){

        assertThat(testUser).usingRecursiveComparison().ignoringFields("id",
                "username",
                "dateAdded",
                "dateLastModified",
                "failedLoginAttempts",
                "isDeleted").isEqualTo(userService.createUser(createUserDto));
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
    void should_returnTrue_when_comparing_plaintextPassword_to_hashedPasswordProperty() {
        String plaintextPass = "plaintext";
        createUserDto.setPasswordHash(plaintextPass);
        User passwordTest = userService.createUser(createUserDto);
        assertThat(passwordTest).extracting("passwordHash").isNotEqualTo(plaintextPass);
    }

    @Test
    void should_throwException_ifUsernameAlreadyExists(){
        when(userRepository.existsByUsername("testEmail@email.com")).thenReturn(true);
        assertThatThrownBy(() -> userService.createUser(createUserDto)).isInstanceOf(UsernameAlreadyExistsException.class);
    }

    @Test
    void should_returnTrue_when_comparingReturnedUpdatedUser_with_testUser() {
        ModelMapper mapper = new ModelMapper();
        UpdateUserDto updateUserDto = mapper.map(createUserDto, UpdateUserDto.class);
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(testUser));
        assertThat(testUser).usingRecursiveComparison().ignoringFields(
                "dateLastModified").isEqualTo(userService.updateUser(updateUserDto, 1));
    }

    @Test
    void should_returnFalse_when_comparingDateLastModified_of_updateUserDto_to_userTest()
    {
        ModelMapper mapper = new ModelMapper();
        UpdateUserDto updateUserDto = mapper.map(createUserDto, UpdateUserDto.class);
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(testUser));
        assertThat(testUser.getDateLastModified()).isNotEqualTo(userService.updateUser(updateUserDto, 1).getDateLastModified());
    }

    @Test
    void should_returnFalse_whenComparing_OriginalUserFromRepo_to_updatedUserDto() {
        ModelMapper mapper = new ModelMapper();
        UpdateUserDto updateUserDto = mapper.map(createUserDto, UpdateUserDto.class);
        updateUserDto.setSurname("change");
        updateUserDto.setTelephone("1234");
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(testUser));
        assertThat(testUser).isNotEqualTo(userService.updateUser(updateUserDto, 1));
    }

    @Test
    void should_throwException_when_deleteUserThatDoesNotExist() {
        assertThatThrownBy(() -> userService.deleteUser(1)).isInstanceOf(UserNotFoundException.class);
    }

    private void createTestDto() {
        createUserDto = new CreateUserDto();
        createUserDto.setForenames("firstName");
        createUserDto.setSurname("lastName");
        createUserDto.setEmailAddress("testEmail@email.com");
        createUserDto.setTelephone("0121123456");
        createUserDto.setDateOfBirth(LocalDate.of(1994, 4, 2));
        try {
            createUserDto.setPasswordHash("test");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private User createTestUser() {
        User userTestReturn = new User();
        userTestReturn.setId(1);
        userTestReturn.setForenames("firstName");
        userTestReturn.setSurname("lastName");
        userTestReturn.setEmailAddress("testEmail@email.com");
        userTestReturn.setTelephone("0121123456");
        userTestReturn.setDateOfBirth(LocalDate.of(1994, 4, 2));
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