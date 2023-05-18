package com.jumar.user.services.impl;

import com.jumar.user.dto.AddAddressDto;
import com.jumar.user.dto.CreateUserDto;
import com.jumar.user.dto.ReadUserDto;
import com.jumar.user.dto.UpdateUserDto;
import com.jumar.user.exceptions.UserNotFoundException;
import com.jumar.user.exceptions.UsernameAlreadyExistsException;
import com.jumar.user.models.Address;
import com.jumar.user.models.User;
import com.jumar.user.repository.AddressRepository;
import com.jumar.user.repository.UserRepository;
import com.jumar.user.services.UserService;
import com.jumar.user.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @SneakyThrows
    @Override
    public User createUser(CreateUserDto createUserDto) {

        if(userRepository.existsByUsername(createUserDto.getEmailAddress())) {
            throw new UsernameAlreadyExistsException("This username already exists");
        }
        ModelMapper mapper = new ModelMapper();

        User newUser = mapper.map(createUserDto, User.class);
        newUser.setDateAdded(LocalDateTime.now());
        newUser.setDateLastModified(newUser.getDateAdded());
        newUser.setUsername(newUser.getEmailAddress());
        newUser.setPasswordHash(PasswordUtils.hashPassword(newUser.getPasswordHash()));

        userRepository.save(newUser);

        return newUser;
    }
    @Override
    public ReadUserDto getUser(int id) throws UserNotFoundException {
        ModelMapper mapper = new ModelMapper();
        User response = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User does not exist."));

        return mapper.map(response, ReadUserDto.class);
    }

    @SneakyThrows
    @Override
    public User updateUser(UpdateUserDto updateUserDto, int id) throws UserNotFoundException {
        ModelMapper mapper = new ModelMapper();

        User response = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User does not exist."));

        User updateUser = mapper.map(updateUserDto, User.class);
        updateUser.setId(response.getId());
        updateUser.setDateAdded(response.getDateAdded());
        updateUser.setDateLastModified(LocalDateTime.now());
        updateUser.setUsername(updateUser.getEmailAddress());
        updateUser.setPasswordHash(PasswordUtils.hashPassword(updateUser.getPasswordHash()));

        userRepository.save(updateUser);

        return updateUser;
    }

    @Override
    public String deleteUser(int id) throws UserNotFoundException {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException("User does not exist.");
        }

        userRepository.deleteById(id);

        return "User deleted";
    }

    @Override
    public Address addAddress(AddAddressDto addAddressDto, int id) {
        return null;
    }


}
