package com.duplicate.requests.avoid.service;

import org.springframework.stereotype.Service;

import com.duplicate.requests.avoid.dto.user.UserDataDto;
import com.duplicate.requests.avoid.mapper.UserDataMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDataService {

    UserDataMapper userDataMapper;

    public int insert(int userIdx) {

        return this.insert(new UserDataDto(userIdx));
    }

    public int insert(UserDataDto userData) {
        return this.userDataMapper.insert(userData);
    }
}
