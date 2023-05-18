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
        try{
            userService.createUser(createUserDto);
            return new ResponseEntity<>("User created successfully.", HttpStatus.CREATED);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>("User creation failed.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<ReadUserDto> getUser(@PathVariable int id) {
        try {
            return new ResponseEntity<>(userService.getUser(id), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UpdateUserDto updateUserDto, @PathVariable int id){
        try{
            userService.updateUser(updateUserDto, id);
            return new ResponseEntity<>("User updated successfully.", HttpStatus.CREATED);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>("User update failed.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        try{
            return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>("User Delete failed.", HttpStatus.BAD_REQUEST);
        }
    }

    /*Implementation of Addresses will come later - commented out for now*/

//    @PostMapping("user/{id}/addresses")
//    public ResponseEntity<String> addAddressToUser(@Valid @RequestBody AddAddressDto addAddressDto, @PathVariable int id) {
//        try {
//            userService.addAddress(addAddressDto, id);
//            return new ResponseEntity<>("Address added successfully.", HttpStatus.CREATED);
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//            return new ResponseEntity<>("Add address failed.", HttpStatus.BAD_REQUEST);
//        }
//    }



}
