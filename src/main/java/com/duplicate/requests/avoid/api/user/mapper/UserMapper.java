package com.duplicate.requests.avoid.api.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.duplicate.requests.avoid.api.user.dto.UserDto;

@Mapper
@Repository
public interface UserMapper {
    // Create
    int insert(UserDto user);

    // Read
    UserDto get(UserDto user);

    // Update
    int update(UserDto user);
}
