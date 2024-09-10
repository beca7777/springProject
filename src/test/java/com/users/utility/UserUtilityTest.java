package com.users.utility;

import com.users.dto.UserDto;
import com.users.entities.User;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class UserUtilityTest {

    private static final String MAIL = "@yahoo.com";

    private static final int LENGTH = 8;

    private static final boolean USE_LETTERS = true;

    private static final boolean USE_NUMBERS = false;

    public static List<User> createUsersEntity(int number) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            User user = createUserEntity();
            user.setId(user.getId() + i);
            user.setDateOfBirth(user.getDateOfBirth().minus(Duration.ofDays(i)));
            user.setPrimaryEmail(generateString() + i + MAIL);
            user.setSecondaryEmails(List.of(generateString() + i + MAIL));

            users.add(user);
        }
        return users;
    }

    public static User createUserEntity() {
        User user = new User();
        user.setId(1L);
        user.setDateOfBirth(Instant.parse("1995-02-20T14:30:00Z"));
        user.setPrimaryEmail(generateString() + MAIL);
        user.setSecondaryEmails(List.of(generateString() + MAIL));
        return user;
    }

    public static List<UserDto> createUsersDto(int number) {
        List<UserDto> usersDto = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            UserDto userDto = createUserDto();
            userDto.setId(userDto.getId() + i);
            userDto.setDateOfBirth(userDto.getDateOfBirth().minus(Duration.ofDays(i)));
            userDto.setPrimaryEmail(generateString() + i + MAIL);
            userDto.setSecondaryEmails(List.of(generateString() + i + MAIL));

            usersDto.add(userDto);
        }
        return usersDto;
    }

    public static UserDto createUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setDateOfBirth(Instant.parse("1995-02-20T14:30:00Z"));
        userDto.setPrimaryEmail(generateString() + MAIL);
        userDto.setSecondaryEmails(List.of(generateString() + MAIL));
        return userDto;
    }

    public static String generateString() {
        return RandomStringUtils.random(LENGTH, USE_LETTERS, USE_NUMBERS);
    }
}
