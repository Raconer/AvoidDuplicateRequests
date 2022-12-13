package com.duplicate.requests.avoid.api.service;

import org.springframework.stereotype.Service;

import com.duplicate.requests.avoid.api.mapper.SignMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SignService {

    SignMapper signMapper;

    public int getCount() {
        return signMapper.count();
    }

}
