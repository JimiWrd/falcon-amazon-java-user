package com.jumar.user.services.impl;

import com.jumar.user.dto.AddAddressDto;
import com.jumar.user.dto.CreateUserDto;
import com.jumar.user.dto.UpdateUserDto;
import com.jumar.user.dto.UserDto;
import com.jumar.user.exceptions.UserCreateFailedException;
import com.jumar.user.exceptions.UserNotFoundException;
import com.jumar.user.exceptions.UsernameAlreadyExistsException;
import com.jumar.user.models.Address;
import com.jumar.user.models.User;
import com.jumar.user.repository.UserRepository;
import com.jumar.user.repository.UserTokenRepository;
import com.jumar.user.services.UserService;
import com.jumar.user.utils.PasswordUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;

    @SneakyThrows
    @Override
    public User createUser(CreateUserDto createUserDto) {
        Optional<User> response = userRepository.findByUsername(
                createUserDto.getEmailAddress());
        if (response.isPresent() && response.get().isDeleted()) {
            throw new UserCreateFailedException("Issue with user creation please contact support via Email");
        }
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
                .passwordHash(PasswordUtils.hashPassword(createUserDto.getPassword()))
                .dateAdded(currentTime)
                .dateLastModified(currentTime)
                .failedLoginAttempts(0)
                .deleted(false)
                .build();

        Integer generatedId = userRepository.save(newUser).getId();
        newUser.setId(generatedId);

        return newUser;
    }

    @Override
    public UserDto getUser(int id) throws UserNotFoundException {
        ModelMapper mapper = new ModelMapper();
        User response = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User does not exist."));
        validateIsDeleted(response);
        return mapper.map(response, UserDto.class);
    }

    @SneakyThrows
    @Override
    public User updateUser(UpdateUserDto updateUserDto, int id) throws UserNotFoundException {
        User response = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User does not exist."));
        validateIsDeleted(response);
        User updateUser = User.builder()
                .id(response.getId())
                .forenames(updateUserDto.getForenames())
                .surname(updateUserDto.getSurname())
                .emailAddress(updateUserDto.getEmailAddress())
                .telephone(updateUserDto.getTelephone())
                .dateOfBirth(updateUserDto.getDateOfBirth())
                .username(updateUserDto.getEmailAddress())
                .passwordHash(PasswordUtils.hashPassword(updateUserDto.getPassword()))
                .dateAdded(response.getDateAdded())
                .dateLastModified(LocalDateTime.now())
                .failedLoginAttempts(response.getFailedLoginAttempts())
                .deleted(false)
                .build();

        userRepository.save(updateUser);

        return updateUser;
    }

    @Override
    @Transactional
    public UserDto deleteUser(int id) throws UserNotFoundException {
        User response = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User does not exist."));

        response.setDeleted(true);

        userRepository.save(response);
        userTokenRepository.deleteByUserId(response.getId());

        return UserDto.builder().userId(response.getId()).build();
    }

    private void validateIsDeleted(User response) {
        if (response.isDeleted()) {
            throw new UserNotFoundException("User does not exist.");
        }
    }
}
