package com.duplicate.requests.avoid.api.userData;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.duplicate.requests.avoid.api.userData.model.UserData;

@Mapper
@Repository
public interface UserDataMapper {
    // Create
    int insert(UserData userData);
}
