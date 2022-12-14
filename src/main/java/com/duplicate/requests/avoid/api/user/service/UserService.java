package com.duplicate.requests.avoid.api.user.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.duplicate.requests.avoid.api.user.dto.UserDto;
import com.duplicate.requests.avoid.api.user.mapper.UserMapper;
import com.duplicate.requests.avoid.utils.PasswordUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private UserMapper userMapper;

    // Create
    public int insert(UserDto userDto) {

        String password = userDto.getPassword();
        userDto.setPassword(PasswordUtil.encoding(password));

        userDto.setRegDate(new Date());

        return userMapper.insert(userDto);
    }

    // Read
    public UserDto get(UserDto userDto) {
        return userMapper.get(userDto);
    }

    // Update
    public int update(UserDto userDto) {
        return userMapper.update(userDto);
    }
}
