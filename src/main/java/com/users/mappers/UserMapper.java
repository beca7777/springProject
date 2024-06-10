package com.users.mappers;

import com.users.dto.UserDto;
import com.users.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
   @Mapping(target = "id" ,ignore = true)
    void updateEntity(@MappingTarget User user, UserDto userDto);
}
