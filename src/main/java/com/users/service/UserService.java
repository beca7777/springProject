package com.users.service;

import com.users.criteria.UserCriteria;
import com.users.criteria.UserCriteriaEasy;
import com.users.dto.PageRequestDto;
import com.users.dto.UserDto;
import com.users.entities.User;
import com.users.exceptions.EntityAlreadyExistsException;
import com.users.exceptions.EntityNotFoundException;
import com.users.mappers.UserMapper;
import com.users.repository.UserRepository;
import com.users.specification.UserSpecification;
import com.users.specification.UserSpecificationV2;
import com.users.util.AdultValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<UserDto> findAllUsers(UserCriteria criteria) {
        log.debug("Request to get all users");
        Specification<User> searchSpecification = new UserSpecification(criteria);
        if (criteria.getPageRequestDto() == null) {
            criteria.setPageRequestDto(new PageRequestDto());
        }
        Pageable pageable = criteria.getPageRequestDto().getPageable(criteria.getPageRequestDto());
        return userRepository.findAll(searchSpecification,pageable).map(userMapper::toDto);
    }

    public Page<UserDto> findAllUsers(UserCriteriaEasy criteria,PageRequestDto pageRequestDto) {
        log.debug("Request to get all users");
        Specification<User> searchSpecification = UserSpecificationV2.createSpecification(criteria);
        Pageable pageable = pageRequestDto.getPageable(pageRequestDto);
        return userRepository.findAll(searchSpecification,pageable).map(userMapper::toDto);
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
}
