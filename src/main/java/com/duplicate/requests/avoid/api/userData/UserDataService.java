package com.duplicate.requests.avoid.api.userData;

import org.springframework.stereotype.Service;

import com.duplicate.requests.avoid.api.userData.model.UserData;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserDataService {

    UserDataMapper userDataMapper;

    public int insert(int userIdx) {

        return this.insert(new UserData(userIdx));
    }

    public int insert(UserData userData) {
        log.info("Insert User Data");
        return userDataMapper.insert(userData);
    }
}
