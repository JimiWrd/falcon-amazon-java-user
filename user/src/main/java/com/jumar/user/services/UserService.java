package com.jumar.user.services;

import com.jumar.user.dto.AddAddressDto;
import com.jumar.user.dto.CreateUserDto;
import com.jumar.user.dto.ReadUserDto;
import com.jumar.user.dto.UpdateUserDto;
import com.jumar.user.exceptions.UserNotFoundException;
import com.jumar.user.models.Address;
import com.jumar.user.models.User;

import java.util.Optional;

public interface UserService {
    User createUser(CreateUserDto createUserDto);
    Address addAddress(AddAddressDto addAddressDto, int id);
    ReadUserDto getUser(int id) throws UserNotFoundException;
    User updateUser(UpdateUserDto updateUserDto, int id) throws UserNotFoundException;
    String deleteUser(int id) throws UserNotFoundException;
}
