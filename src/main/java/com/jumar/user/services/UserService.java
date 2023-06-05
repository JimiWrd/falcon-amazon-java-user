package com.jumar.user.services;

import com.jumar.user.dto.AddAddressDto;
import com.jumar.user.dto.CreateUserDto;
import com.jumar.user.dto.UserDto;
import com.jumar.user.dto.UpdateUserDto;
import com.jumar.user.exceptions.UserNotFoundException;
import com.jumar.user.models.Address;
import com.jumar.user.models.User;

public interface UserService {
    User createUser(CreateUserDto createUserDto);
    UserDto getUser(int id) throws UserNotFoundException;
    User updateUser(UpdateUserDto updateUserDto, int id) throws UserNotFoundException;
    UserDto deleteUser(int id) throws UserNotFoundException;
}
