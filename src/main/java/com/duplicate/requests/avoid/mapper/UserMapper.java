package com.duplicate.requests.avoid.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.duplicate.requests.avoid.dto.UserDto;

@Mapper
@Repository
public interface UserMapper {
    // Create
    int insert(UserDto user);

    // Read
    UserDto get(UserDto user);

    List<UserDto> getList();

    // Update
    int update(UserDto user);
}
