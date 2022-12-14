package com.duplicate.requests.avoid.api.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.duplicate.requests.avoid.api.user.model.User;

@Mapper
@Repository
public interface UserMapper {
    // Create
    int insert(User user);

    // Read
    User get(User user);

    // Update
    int update(User user);
}
