package com.duplicate.requests.avoid.api.userData.service;

import org.springframework.stereotype.Service;

import com.duplicate.requests.avoid.api.userData.dto.UserDataDto;
import com.duplicate.requests.avoid.api.userData.mapper.UserDataMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDataService {

    UserDataMapper userDataMapper;

    public int insert(int userIdx) {

        return this.insert(new UserDataDto(userIdx));
    }

    public int insert(UserDataDto userData) {
        return userDataMapper.insert(userData);
    }
}
