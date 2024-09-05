package com.users.service;

import com.users.criteria.UserCriteriaEasy;
import com.users.dto.UserDto;
import com.users.entities.User;
import com.users.exceptions.EntityAlreadyExistsException;
import com.users.exceptions.EntityNotFoundException;
import com.users.mappers.UserMapper;
import com.users.repository.UserRepository;
import com.users.utility.UserUtilityTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void whenSaveUser_shouldReturnUser() {
        List<UserDto> usersDto = UserUtilityTest.createUsersDto(1);
        UserDto userDto = usersDto.get(0);

        User user = new User();
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setPrimaryEmail(userDto.getPrimaryEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());

        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto createdDto = userService.create(userDto);

        assertThat(createdDto.getDateOfBirth()).isSameAs(userDto.getDateOfBirth());
        assertThat(createdDto.getPrimaryEmail()).isSameAs(userDto.getPrimaryEmail());
        assertThat(createdDto.getPhoneNumber()).isSameAs(userDto.getPhoneNumber());

        verify(userRepository).save(user);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void whenCreateUser_withPrimaryEmailInSecondaryEmails_shouldThrowException() {
        List<UserDto> usersDto = UserUtilityTest.createUsersDto(1);
        UserDto userDto = usersDto.get(0);
        userDto.setSecondaryEmails(Arrays.asList(userDto.getPrimaryEmail(), "another@yahoo.com"));

        userService.create(userDto);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void whenCreateUser_withSecondaryEmailAlreadyExistingAsPrimary_shouldThrowException() {
        List<UserDto> usersDto = UserUtilityTest.createUsersDto(1);
        UserDto userDto = usersDto.get(0);
        userDto.setSecondaryEmails(List.of("existing@yahoo.com"));

        when(userRepository.existsByPrimaryEmail("existing@yahoo.com")).thenReturn(true);

        userService.create(userDto);
    }

    @Test
    public void whenGivenId_shouldDeleteUser_ifFound() {
        List<User> usersEntity = UserUtilityTest.createUsersEntity(1);
        User userEntity = usersEntity.get(0);

        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));

        userService.deleteUser(userEntity.getId());

        verify(userRepository).deleteById(userEntity.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void whenDeleteUser_shouldThrowException_ifUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        userService.deleteUser(99L);
    }

    @Test
    public void shouldReturnAllUsers() {
        UserCriteriaEasy criteria = new UserCriteriaEasy();
        Pageable pageable = PageRequest.of(0, 10);

        List<UserDto> usersDto = UserUtilityTest.createUsersDto(2);
        UserDto userDto1 = usersDto.get(0);
        UserDto userDto2 = usersDto.get(1);

        User user1 = new User();
        user1.setDateOfBirth(userDto1.getDateOfBirth());
        user1.setPrimaryEmail(userDto1.getPrimaryEmail());
        user1.setPhoneNumber(userDto1.getPhoneNumber());
        user1.setSecondaryEmails(userDto1.getSecondaryEmails());

        User user2 = new User();
        user2.setDateOfBirth(userDto2.getDateOfBirth());
        user2.setPrimaryEmail(userDto2.getPrimaryEmail());
        user2.setPhoneNumber(userDto2.getPhoneNumber());
        user2.setSecondaryEmails(userDto2.getSecondaryEmails());

        List<User> users = Arrays.asList(user1, user2);
        Page<User> userPage = new PageImpl<>(users);

        when(userRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(userPage);
        when(userMapper.toDto(user1)).thenReturn(userDto1);
        when(userMapper.toDto(user2)).thenReturn(userDto2);

        Page<UserDto> result = userService.findAllUsers(criteria, pageable);

        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent().size()).isEqualTo(2);
    }

    @Test
    public void whenFindAllUsers_withNoMatchingUsers_shouldReturnEmptyPage() {
        UserCriteriaEasy criteria = new UserCriteriaEasy();
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> emptyPage = Page.empty(pageable);

        when(userRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(emptyPage);

        Page<UserDto> result = userService.findAllUsers(criteria, pageable);

        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.getContent().size()).isEqualTo(0);
    }

    @Test
    public void whenFindUserById_shouldReturnUserDto_ifFound() {
        List<User> usersEntity = UserUtilityTest.createUsersEntity(1);
        User userEntity = usersEntity.get(0);

        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setDateOfBirth(userEntity.getDateOfBirth());
        userDto.setPrimaryEmail(userEntity.getPrimaryEmail());
        userDto.setSecondaryEmails(userEntity.getSecondaryEmails());

        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        when(userMapper.toDto(userEntity)).thenReturn(userDto);

        UserDto foundUser = userService.findUserById(1L);

        assertThat(foundUser).isEqualTo(userDto);
    }

    @Test(expected = EntityNotFoundException.class)
    public void whenFindUserById_shouldThrowException_ifNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        userService.findUserById(99L);
    }

    @Test
    public void whenGivenId_shouldUpdateUser_ifFound() {
        List<User> usersEntity = UserUtilityTest.createUsersEntity(1);
        User userEntity = usersEntity.get(0);

        List<UserDto> usersDto = UserUtilityTest.createUsersDto(1);
        UserDto userDto1 = usersDto.get(0);

        given(userRepository.findById(userEntity.getId())).willReturn(Optional.of(userEntity));

        userService.updateUser(userEntity.getId(), userDto1);
        verify(userRepository).findById(userEntity.getId());

        User updatedUser = userEntity;
        updatedUser.setPrimaryEmail(userDto1.getPrimaryEmail());
        updatedUser.setDateOfBirth(userDto1.getDateOfBirth());
        updatedUser.setSecondaryEmails(userDto1.getSecondaryEmails());

        assertThat(userEntity.getPrimaryEmail()).isEqualTo(userDto1.getPrimaryEmail());
        assertThat(userEntity.getDateOfBirth()).isEqualTo(userDto1.getDateOfBirth());
        assertThat(userEntity.getPhoneNumber()).isEqualTo(userDto1.getPhoneNumber());
    }

    @Test(expected = EntityNotFoundException.class)
    public void whenUpdateUser_withNonExistingUser_shouldThrowEntityNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        List<UserDto> usersDto = UserUtilityTest.createUsersDto(1);
        UserDto userDto1 = usersDto.get(0);

        userService.updateUser(89L, userDto1);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void whenUpdateUser_withExistingPrimaryEmail_shouldThrowEntityAlreadyExistsException() {
        List<UserDto> usersDto = UserUtilityTest.createUsersDto(1);
        UserDto userDto1 = usersDto.get(0);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(userRepository.existsByPrimaryEmail(userDto1.getPrimaryEmail())).thenReturn(true);

        userService.updateUser(1L, userDto1);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void whenUpdateUser_withSecondaryEmailSameAsPrimary_shouldThrowEntityAlreadyExistsException() {
        UserDto userDto = new UserDto();
        userDto.setPrimaryEmail("newprimary@yahoo.com");
        userDto.setDateOfBirth(Instant.parse("1998-02-20T14:30:00Z"));
        userDto.setSecondaryEmails(List.of("newprimary@yahoo.com"));

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setPrimaryEmail("oldprimary@yahoo.com");
        existingUser.setDateOfBirth(Instant.parse("1998-02-20T14:30:00Z"));
        existingUser.setSecondaryEmails(List.of("oldsecondary@yahoo.com"));

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByPrimaryEmail("newprimary@yahoo.com")).thenReturn(true);

        userService.updateUser(1L, userDto);
    }
}
