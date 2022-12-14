package com.duplicate.requests.avoid.api.sign.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.duplicate.requests.avoid.api.sign.dto.AuthDto;
import com.duplicate.requests.avoid.api.sign.dto.SignDto;
import com.duplicate.requests.avoid.api.user.dto.AccountDto;
import com.duplicate.requests.avoid.api.user.dto.UserDto;
import com.duplicate.requests.avoid.api.user.service.UserService;
import com.duplicate.requests.avoid.api.userData.service.UserDataService;
import com.duplicate.requests.avoid.utils.JwtUtil;
import com.duplicate.requests.avoid.utils.PasswordUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SignService implements UserDetailsService {

    UserService userService;
    UserDataService userDataService;

    // CREATE
    public int signUp(UserDto user) {
        if (userService.insert(user) == 1) {
            return userDataService.insert(user.getIdx());
        }
        return 0;
    }

    // READ
    public AuthDto auth(AccountDto accountDto) {
        UserDto user = this.findByEmail(accountDto.getEmail());
        if (user == null)
            // 계정이 없을떄
            return null;
        else if (!PasswordUtil.equals(accountDto.getPassword(), user.getPassword())) {
            // 비밀번호가 틀렷을때
            return null;
        }
        String token = JwtUtil.create(user.getEmail());
        String refreshToken = JwtUtil.createRefresh();

        // Refresh Token
        int updateCnt = userService.update(UserDto.builder().idx(user.getIdx()).refreshToken(refreshToken).build());

        if (updateCnt == 1) {
            return AuthDto.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .token(token)
                    .refreshToken(refreshToken).build();
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto userDto = this.findByEmail(username);

        if (userDto == null) {
            throw new UsernameNotFoundException("not found : " + username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
        User user = new User(userDto.getEmail(), userDto.getPassword(), grantedAuthorities);
        return new SignDto(userDto, user);
    }

    public UserDto findByEmail(String email) {
        return userService.get(new UserDto(email));
    }
}
