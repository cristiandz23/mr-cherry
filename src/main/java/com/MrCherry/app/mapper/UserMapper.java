package com.MrCherry.app.mapper;

import com.MrCherry.app.dto.UserRequest;
import com.MrCherry.app.dto.UserResponse;
import com.MrCherry.app.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUserFromRequest(UserRequest userRequest);

    UserResponse toResponseFromUser(User user);

}
