package com.users.controller;

import com.users.dto.RequestDto;
import com.users.dto.UserDto;
import com.users.entities.User;
import com.users.repository.UserRepository;
import com.users.service.FilterSpecificationService;
import com.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/filter")
public class FilterController {
    @Autowired
    private UserService userService;

    @Autowired
    private FilterSpecificationService<User> filterSpecificationService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping("/{dateOfBirth}")
    public ResponseEntity<List<UserDto>> getByDateOfBirth(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant dateOfBirth) {
        return ResponseEntity.ok(userService.findByDateOfBirth(dateOfBirth));
    }

    @PostMapping("/specification")
    public ResponseEntity<List<UserDto>> getUsers(@RequestBody RequestDto requestDto) {
        Specification<User> searchSpecification = filterSpecificationService.getSearchSpecification(requestDto.getSearchRequestDto(), requestDto.getGlobalOperator());
        List<UserDto> all = userService.findAllSpecification(searchSpecification);
        return ResponseEntity.ok(all);
    }
}
