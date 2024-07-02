package com.users.controller;

import com.users.criteria.UserCriteria;
import com.users.dto.UserDto;
import com.users.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<UserDto>> findAll(UserCriteria criteria) {
        log.info("REST request to find all users");
        return ResponseEntity.ok(userService.findAllUsers(criteria));
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
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        log.info("Find user with id {}",id);
        return ResponseEntity.ok(userService.findUserById(id));
    }

}
