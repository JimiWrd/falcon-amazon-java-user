package com.jumar.user.controllers;

import com.jumar.user.dto.CreateUserDto;
import com.jumar.user.models.User;
import com.jumar.user.repository.AddressRepository;
import com.jumar.user.repository.UserRepository;
import com.jumar.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class UserController {

    private final UserService userService;

    @PostMapping("users/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        return new ResponseEntity<>(userService.createUser(createUserDto), HttpStatus.CREATED);
    }

}
