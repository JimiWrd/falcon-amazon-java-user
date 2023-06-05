package com.jumar.user.controllers;

import com.jumar.user.dto.CreateUserDto;
import com.jumar.user.dto.ResponseBodyDto;
import com.jumar.user.dto.UserDto;
import com.jumar.user.dto.UpdateUserDto;
import com.jumar.user.models.User;
import com.jumar.user.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class UserController {

    private final UserService userService;

    @PostMapping("user/create")
    public ResponseEntity<ResponseBodyDto> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        User newUser = userService.createUser(createUserDto);

        UserDto newUserDto = UserDto
                .builder()
                .userId(newUser.getId())
                .emailAddress(newUser.getEmailAddress())
                .build();
        return new ResponseEntity<>(ResponseBodyDto
                .builder()
                .success(true)
                .response(newUserDto)
                .message(null)
                .timestamp(LocalDateTime.now()).build(), HttpStatus.CREATED);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<ResponseBodyDto> getUser(@PathVariable int id) {
        UserDto user = userService.getUser(id);
        return new ResponseEntity<>(ResponseBodyDto
                .builder()
                .success(true)
                .response(user)
                .message(null)
                .timestamp(LocalDateTime.now()).build(), HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<ResponseBodyDto> updateUser(@Valid @RequestBody UpdateUserDto updateUserDto, @PathVariable int id){
        User updatedUser = userService.updateUser(updateUserDto, id);
        UserDto updatedUserDto = UserDto
                .builder()
                .userId(updatedUser.getId())
                .emailAddress(updatedUser.getEmailAddress())
                .build();
        return new ResponseEntity<>(ResponseBodyDto
                .builder()
                .success(true)
                .response(updatedUserDto)
                .message(null)
                .timestamp(LocalDateTime.now()).build(), HttpStatus.OK);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<ResponseBodyDto> deleteUser(@PathVariable int id) {
        UserDto deletedUserDto = userService.deleteUser(id);

        return new ResponseEntity<>(ResponseBodyDto
                .builder()
                .success(true)
                .response(deletedUserDto)
                .message(null)
                .timestamp(LocalDateTime.now()).build(), HttpStatus.OK);
    }




}
