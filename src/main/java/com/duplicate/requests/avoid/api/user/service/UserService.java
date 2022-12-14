package com.duplicate.requests.avoid.api.user.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.duplicate.requests.avoid.api.user.mapper.UserMapper;
import com.duplicate.requests.avoid.api.user.model.User;
import com.duplicate.requests.avoid.utils.PasswordUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private UserMapper userMapper;

    // Create
    public int insert(User user) {

        String password = user.getPassword();
        user.setPassword(PasswordUtil.encoding(password));

        user.setRegDate(new Date());

        return userMapper.insert(user);
    }

    // Read
    public User get(User user) {
        return userMapper.get(user);
    }

    // Update
    public int update(User user) {
        return userMapper.update(user);
    }
}
