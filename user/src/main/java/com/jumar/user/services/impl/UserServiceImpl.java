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

        LocalDateTime currentTime = LocalDateTime.now();

        User newUser = User.builder()
                .forenames(createUserDto.getForenames())
                .surname(createUserDto.getSurname())
                .emailAddress(createUserDto.getEmailAddress())
                .telephone(createUserDto.getTelephone())
                .dateOfBirth(createUserDto.getDateOfBirth())
                .username(createUserDto.getEmailAddress())
                .passwordHash(PasswordUtils.hashPassword(createUserDto.getPasswordHash()))
                .dateAdded(currentTime)
                .dateLastModified(currentTime)
                .failedLoginAttempts(0)
                .isDeleted(false)
                .build();

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
        User response = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User does not exist."));

        User updateUser = User.builder()
                .id(response.getId())
                .forenames(updateUserDto.getForenames())
                .surname(updateUserDto.getSurname())
                .emailAddress(updateUserDto.getEmailAddress())
                .telephone(updateUserDto.getTelephone())
                .dateOfBirth(updateUserDto.getDateOfBirth())
                .username(updateUserDto.getEmailAddress())
                .passwordHash(PasswordUtils.hashPassword(updateUserDto.getPasswordHash()))
                .dateAdded(response.getDateAdded())
                .dateLastModified(LocalDateTime.now())
                .failedLoginAttempts(response.getFailedLoginAttempts())
                .isDeleted(false)
                .build();

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
