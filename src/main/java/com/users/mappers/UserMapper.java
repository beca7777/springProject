package com.users.mappers;

import com.users.dto.UserDto;
import com.users.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserDto userDto);

    List<UserDto> toDto(List<User> users);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget User user, UserDto userDto);
}
