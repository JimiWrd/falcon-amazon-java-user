package com.jumar.user.controllers;

import com.jumar.user.dto.AddAddressDto;
import com.jumar.user.dto.CreateUserDto;
import com.jumar.user.dto.ReadUserDto;
import com.jumar.user.dto.UpdateUserDto;
import com.jumar.user.models.Address;
import com.jumar.user.models.User;
import com.jumar.user.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class UserController {

    private final UserService userService;

    @PostMapping("user/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return new ResponseEntity<>("User created successfully.", HttpStatus.CREATED);

    }

    @GetMapping("user/{id}")
    public ResponseEntity<ReadUserDto> getUser(@PathVariable int id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.ACCEPTED);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UpdateUserDto updateUserDto, @PathVariable int id){
        userService.updateUser(updateUserDto, id);
        return new ResponseEntity<>("User updated successfully.", HttpStatus.CREATED);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }




}
