package com.users.utility;

import com.users.dto.UserDto;
import com.users.entities.User;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class UserUtilityTest {

    private static final String mail = "@yahoo.com";

    public static List<User> createUsersEntity(int number) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            User user = createUserEntity();
            user.setId(user.getId() + i);
            user.setDateOfBirth(user.getDateOfBirth().minus(Duration.ofDays(i)));
            user.setPrimaryEmail(generateString() + i + mail);
            user.setSecondaryEmails(List.of(generateString() + i + mail));

            users.add(user);
        }
        return users;
    }

    public static User createUserEntity() {
        User user = new User();
        user.setId(1L);
        user.setDateOfBirth(Instant.parse("1995-02-20T14:30:00Z"));
        user.setPrimaryEmail(generateString() + mail);
        user.setSecondaryEmails(List.of(generateString() + mail));
        return user;
    }

    public static List<UserDto> createUsersDto(int number) {
        List<UserDto> usersDto = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            UserDto userDto = createUserDto();
            userDto.setId(userDto.getId() + i);
            userDto.setDateOfBirth(userDto.getDateOfBirth().minus(Duration.ofDays(i)));
            userDto.setPrimaryEmail(generateString() + i + mail);
            userDto.setSecondaryEmails(List.of(generateString() + i + mail));

            usersDto.add(userDto);
        }
        return usersDto;
    }

    public static UserDto createUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setDateOfBirth(Instant.parse("1995-02-20T14:30:00Z"));
        userDto.setPrimaryEmail(generateString() + mail);
        userDto.setSecondaryEmails(List.of(generateString() + mail));
        return userDto;
    }

    public static String generateString() {
        int length = 8;
        boolean useLetters = true;
        boolean useNumbers = false;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }
}
