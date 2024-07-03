package com.users.controller;

import com.users.dto.RequestDto;
import com.users.dto.UserDto;
import com.users.entities.User;
import com.users.service.FilterSpecificationService;
import com.users.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final FilterSpecificationService<User> filterSpecificationService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user) {
        log.info("User to be created {}", user);
        return ResponseEntity.ok(userService.create(user));
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> findAll() {
        log.info("REST request to find all users");
        return ResponseEntity.ok(userService.findAllUsers());
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

    @PostMapping("/filter/specification")
    public ResponseEntity<List<UserDto>> getUsers(@RequestBody RequestDto requestDto) {
        List<UserDto> users = userService.getUsers(requestDto);
        return ResponseEntity.ok(users);
    }
}
