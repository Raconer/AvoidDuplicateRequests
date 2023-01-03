package com.duplicate.requests.avoid.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.duplicate.requests.avoid.dto.user.UserDataDto;

@Mapper
@Repository
public interface UserDataMapper {
    // Create
    int insert(UserDataDto userDataDto);
}
