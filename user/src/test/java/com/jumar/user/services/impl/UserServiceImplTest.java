package com.jumar.user.services.impl;

import com.jumar.user.dto.CreateUserDto;
import com.jumar.user.dto.UpdateUserDto;
import com.jumar.user.exceptions.UserNotFoundException;
import com.jumar.user.exceptions.UsernameAlreadyExistsException;
import com.jumar.user.fixtures.UserFixtures;
import com.jumar.user.models.User;
import com.jumar.user.repository.AddressRepository;
import com.jumar.user.repository.UserRepository;
import com.jumar.user.services.UserService;
import com.jumar.user.utils.PasswordUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

    @InjectMocks
    private UserServiceImpl userService;
    private CreateUserDto createUserDto;
    private User testUser;
    private User createdUser;

    @BeforeEach
    void setup() {
//        userService = new UserServiceImpl(userRepository, addressRepository);
        createUserDto = UserFixtures.generateCreateUserDto();
        testUser = UserFixtures.generateValidUser();
        createdUser = userService.createUser(createUserDto);
    }

    @Test
    void should_returnTrue_when_comparingReturnedUser_to_TestUser(){

        User createdUser = userService.createUser(createUserDto);

        assertThat(testUser).usingRecursiveComparison().ignoringFields("id",
                "username",
                "dateAdded",
                "dateLastModified",
                "failedLoginAttempts",
                "isDeleted").isEqualTo(createdUser);
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
        when(userRepository.existsByUsername("josh.wood@me.com")).thenReturn(true);
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
}