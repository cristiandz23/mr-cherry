package com.MrCherry.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Hola {

    @Autowired
    private UserMapper userMapper;
e
    public void relizarOperacion(){

        UserDto userDto = new UserDto("asd","123");

        User user = userMapper.toUser(userDto);

    }
}
