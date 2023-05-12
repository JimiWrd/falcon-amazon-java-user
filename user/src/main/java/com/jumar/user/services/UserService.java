package com.jumar.user.services;

import com.jumar.user.dto.CreateUserDto;
import com.jumar.user.models.User;

public interface UserService {
    User createUser(CreateUserDto createUserDto);
}
