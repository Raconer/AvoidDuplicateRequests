package com.duplicate.requests.avoid.api.sign;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.duplicate.requests.avoid.api.sign.model.Sign;

@Mapper
@Repository
public interface SignMapper {
    int count();

    int insert(Sign signInfo);
}
