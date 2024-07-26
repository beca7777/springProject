package com.users.controller;

import com.users.criteria.UserCriteria;
import com.users.criteria.UserCriteriaEasy;
import com.users.dto.UserDto;
import com.users.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    public final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user) {
        log.info("User to be created {}", user);
        return ResponseEntity.ok(userService.create(user));
    }

    @GetMapping()
    public ResponseEntity<Page<UserDto>> findAll(UserCriteria criteria,@PageableDefault(size = 5) Pageable pageable) {
        log.info("REST request to find all users");
        return ResponseEntity.ok(userService.findAllUsers(criteria, pageable));
    }

    @GetMapping("/easy")
    public ResponseEntity<Page<UserDto>> findAll(UserCriteriaEasy criteria, @PageableDefault(size = 5) Pageable pageable) {
        log.info("REST request to find all users");
        return ResponseEntity.ok(userService.findAllUsers(criteria, pageable));
    }

    @PostMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        log.info("Update user with id {}", id);
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Delete user with id {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        log.info("Find user with id {}", id);
        return ResponseEntity.ok(userService.findUserById(id));
    }
}
