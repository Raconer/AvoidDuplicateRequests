package com.duplicate.requests.avoid.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SignMapper {
    int count();
}
