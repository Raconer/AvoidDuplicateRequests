package com.duplicate.requests.avoid.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.duplicate.requests.avoid.dto.UserDto;
import com.duplicate.requests.avoid.mapper.UserMapper;
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

        return this.userMapper.insert(userDto);
    }

    // Read
    public UserDto get(UserDto userDto) {
        return this.userMapper.get(userDto);
    }

    public List<UserDto> getList() {
        return this.userMapper.getList();
    }

    // Update
    public int update(UserDto userDto) {
        return this.userMapper.update(userDto);
    }
}
