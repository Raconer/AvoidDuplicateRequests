package com.duplicate.requests.avoid.api.sign;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.duplicate.requests.avoid.api.sign.model.Sign;
import com.duplicate.requests.avoid.api.userData.UserDataService;
import com.duplicate.requests.avoid.utils.PasswordUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class SignService {

    SignMapper signMapper;
    UserDataService userDataService;

    // 삭제 예정
    public int getCount() {
        return signMapper.count();
    }

    public int insert(Sign signInfo) {

        String password = signInfo.getPassword();
        signInfo.setPassword(PasswordUtil.encoding(password));

        signInfo.setRegDate(new Date());
        if (signMapper.insert(signInfo) == 1) {
            log.info("User Insert Success : {}", signInfo.getEmail());
            return userDataService.insert(signInfo.getIdx());
        }

        return 0;
    }
}
