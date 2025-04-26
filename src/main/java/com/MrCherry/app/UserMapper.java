package com.MrCherry.app;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userDto(User user);
    User toUser(UserDto userDto);

}
