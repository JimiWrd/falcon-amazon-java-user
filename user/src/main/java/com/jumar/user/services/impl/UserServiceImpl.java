package com.jumar.user.services.impl;

import com.jumar.user.dto.CreateUserDto;
import com.jumar.user.exceptions.OneOrMoreUserRequiredFieldsNullException;
import com.jumar.user.exceptions.UserCreateFailedException;
import com.jumar.user.exceptions.validation.UserValidator;
import com.jumar.user.models.User;
import com.jumar.user.repository.AddressRepository;
import com.jumar.user.repository.UserRepository;
import com.jumar.user.services.UserService;
import com.jumar.user.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    public User createUser(CreateUserDto createUserDto) {
        if(UserValidator.dtoFieldsAreNull(createUserDto)){
            throw new OneOrMoreUserRequiredFieldsNullException("One or more fields were empty.");
        }

        ModelMapper mapper = new ModelMapper();

        User newUser = mapper.map(createUserDto, User.class);
        newUser.setDateAdded(LocalDateTime.now());
        newUser.setDateLastModified(newUser.getDateAdded());
        newUser.setUsername(newUser.getEmailAddress());
        try{
            newUser.setPasswordHash(PasswordUtils.hashPassword(newUser.getPasswordHash()));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }

        try {
            userRepository.save(newUser);
        } catch (Exception e) {
            throw new UserCreateFailedException("There was an error whilst creating this account.");
        }

        return newUser;
    }
}
