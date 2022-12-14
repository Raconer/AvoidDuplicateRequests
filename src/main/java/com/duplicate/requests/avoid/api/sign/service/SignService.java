package com.duplicate.requests.avoid.api.sign.service;

import org.springframework.stereotype.Service;

import com.duplicate.requests.avoid.api.sign.model.Auth;
import com.duplicate.requests.avoid.api.user.model.Account;
import com.duplicate.requests.avoid.api.user.model.User;
import com.duplicate.requests.avoid.api.user.service.UserService;
import com.duplicate.requests.avoid.api.userData.service.UserDataService;
import com.duplicate.requests.avoid.utils.JwtUtil;
import com.duplicate.requests.avoid.utils.PasswordUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SignService {

    UserService userService;
    UserDataService userDataService;

    // CREATE
    public int signUp(User user) {
        if (userService.insert(user) == 1) {
            return userDataService.insert(user.getIdx());
        }
        return 0;
    }

    // READ
    public Auth auth(Account account) {
        User user = this.findByEmail(account.getEmail());
        if (user == null)
            // 계정이 없을떄
            return null;
        else if (!PasswordUtil.equals(account.getPassword(), user.getPassword())) {
            // 비밀번호가 틀렷을때
            return null;
        }
        String token = JwtUtil.create(user.getEmail());
        String refreshToken = JwtUtil.createRefresh();

        // Refresh Token
        int updateCnt = userService.update(User.builder().idx(user.getIdx()).refreshToken(refreshToken).build());

        if (updateCnt == 1) {
            return Auth.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .token(token)
                    .refreshToken(refreshToken).build();
        }
        return null;
    }

    public User findByEmail(String email) {
        return userService.get(new User(email));
    }

}
