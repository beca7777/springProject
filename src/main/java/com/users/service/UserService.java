package com.users.service;

import com.users.dto.UserDto;
import com.users.entities.User;
import com.users.exceptions.EntityAlreadyExistsException;
import com.users.exceptions.EntityNotFoundException;
import com.users.mappers.UserMapper;
import com.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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
        List<User> allByEmail = userRepository.findAllByEmail(user.getEmail());
        if (!allByEmail.isEmpty()) {
            throw new EntityAlreadyExistsException(String.format("User with email %s already exists", user.getEmail()));
        }
        validateDateOfBirth(user.getDateOfBirth());
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        log.info("Update user with id {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %s doesn't exist", id)));
        validateDateOfBirth(user.getDateOfBirth());
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

    public void validateDateOfBirth(Instant ageOfBirth) {
        LocalDate birthDate = LocalDate.ofInstant(ageOfBirth, ZoneId.systemDefault());
        LocalDate now = LocalDate.now();
        Period period = Period.between(birthDate, now);
        if (period.getYears() < 18) {
            throw new IllegalArgumentException("User must be older than 18 years.");
        }
    }

    public User findById(Long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(String.format("User with id %s doesn't exist", id)));
    }
}
