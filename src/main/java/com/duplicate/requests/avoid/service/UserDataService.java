package com.duplicate.requests.avoid.service;

import org.springframework.stereotype.Service;

import com.duplicate.requests.avoid.mapper.UserDataMapper;
import com.duplicate.requests.avoid.model.dto.user.UserDataDto;

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
