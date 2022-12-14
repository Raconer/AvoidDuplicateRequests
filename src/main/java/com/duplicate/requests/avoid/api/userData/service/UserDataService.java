package com.duplicate.requests.avoid.api.userData.service;

import org.springframework.stereotype.Service;

import com.duplicate.requests.avoid.api.userData.mapper.UserDataMapper;
import com.duplicate.requests.avoid.api.userData.model.UserData;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDataService {

    UserDataMapper userDataMapper;

    public int insert(int userIdx) {

        return this.insert(new UserData(userIdx));
    }

    public int insert(UserData userData) {
        return userDataMapper.insert(userData);
    }
}
