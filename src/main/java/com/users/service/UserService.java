package com.users.service;

import com.users.dto.UserDto;
import com.users.entities.User;
import com.users.exceptions.EntityAlreadyExistsException;
import com.users.exceptions.EntityNotFoundException;
import com.users.mappers.UserMapper;
import com.users.repository.UserRepository;
import com.users.util.SpringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@AllArgsConstructor
@Service
@Slf4j
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserDto create(User user) {
        log.info("User to be created {}", user);

        if (!SpringUtils.validateDateOfBirth(user.getDateOfBirth())) {
            throw new IllegalArgumentException("User must be 18 years old");
        }

        if (userRepository.existByEmail(user.getEmail())) {
            throw new EntityAlreadyExistsException(String.format("User with email %s already exists", user.getEmail()));
        }

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        log.info("Update user with id {}", id);
        if (!SpringUtils.validateDateOfBirth(userDto.getDateOfBirth())) {
            throw new IllegalArgumentException("User must be 18 years old");

        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %s doesn't exist", id)));
        userMapper.updateEntity(user, userDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);

    }

    public List<UserDto> findAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        log.info("Delete user with id {}", id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(String.format("User with id %s doesn't exist", id));
        }
    }


    public User findById(Long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(String.format("User with id %s doesn't exist", id)));
    }
}
