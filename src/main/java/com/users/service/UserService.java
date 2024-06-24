package com.users.service;

import com.users.dto.RequestDto;
import com.users.dto.UserDto;
import com.users.entities.User;
import com.users.exceptions.EntityAlreadyExistsException;
import com.users.exceptions.EntityNotFoundException;
import com.users.mappers.UserMapper;
import com.users.repository.UserRepository;
import com.users.util.AdultValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@AllArgsConstructor
@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserDto create(UserDto user) {
        log.debug("User to be created {}", user);
        if (!AdultValidator.validateDateOfBirth(user.getDateOfBirth())) {
            throw new IllegalArgumentException("User must be 18 years old");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EntityAlreadyExistsException(String.format("User with email %s already exists", user.getEmail()));
        }
        User userEntity = userMapper.toEntity(user);
        User savedUser = userRepository.save(userEntity);
        return userMapper.toDto(savedUser);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        log.debug("Update user with id {}", id);
        if (!AdultValidator.validateDateOfBirth(userDto.getDateOfBirth())) {
            throw new IllegalArgumentException("User must be 18 years old");
        }
        User user = findById(id);
        userMapper.updateEntity(user, userDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public List<UserDto> findAllUsers() {
        log.debug("Request to get all users");
        List<User> allUsers = userRepository.findAll();
        return userMapper.toDto(allUsers);
    }

    public void deleteUser(Long id) {
        log.debug("Delete user with id {}", id);
        findById(id);
        userRepository.deleteById(id);
    }

    public UserDto findUserById(Long id) {
        log.debug("Find user with id {}", id);
        return userMapper.toDto(findById(id));
    }

    private User findById(Long id) {
        log.debug("Find user with id {}", id);
        return userRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(String.format("User with id %s doesn't exist", id)));
    }

    public UserDto findByEmail(String email) {
        User byEmail = userRepository.findByEmail(email);
        return userMapper.toDto(byEmail);
    }

    public List<UserDto> findByDateOfBirth(Instant dateOfBirth) {
        List<User> byDateOfBirth = userRepository.findByDateOfBirth(dateOfBirth);
        return userMapper.toDto(byDateOfBirth);
    }

    public List<UserDto> findAllSpecification(Specification<User> userSpecification) {
        List<User> all = userRepository.findAll(userSpecification);
        return userMapper.toDto(all);
    }
}
