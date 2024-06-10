package com.users.endpoint;

import com.users.dto.UserDto;
import com.users.entities.User;
import com.users.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class UserEndPoint {
    public final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createStadium(@RequestBody User user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PostMapping("/update-user")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity<Void> deleteManager(@PathVariable(name = "user-id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
