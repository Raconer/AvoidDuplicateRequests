package com.duplicate.requests.avoid.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.duplicate.requests.avoid.model.dto.auth.AccountDto;
import com.duplicate.requests.avoid.model.dto.auth.AuthDto;
import com.duplicate.requests.avoid.model.dto.auth.SignDto;
import com.duplicate.requests.avoid.model.dto.user.UserDto;
import com.duplicate.requests.avoid.utils.JwtUtil;
import com.duplicate.requests.avoid.utils.PasswordUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SignService implements UserDetailsService {

    UserService userService;
    UserDataService userDataService;

    // CREATE
    public int signUp(UserDto userDto) {

        if (this.findByEmail(userDto.getEmail()) != null) {
            return 0;
        }

        if (userService.insert(userDto) == 1) {
            return this.userDataService.insert(userDto.getIdx());
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

        return this.setAuthDto(user.getIdx(), user.getEmail(), user.getName());
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

    // Refresh Token
    public AuthDto refresh(String refreshToken) {

        if (JwtUtil.getData(refreshToken) == null) {
            return null;
        }

        UserDto userDto = UserDto.builder().refreshToken(refreshToken).build();
        userDto = userService.get(userDto);

        if (userDto == null) {
            return null;
        }

        return this.setAuthDto(userDto.getIdx(), userDto.getEmail(), userDto.getName());
    }

    public AuthDto setAuthDto(int idx, String email, String name) {

        String token = JwtUtil.create(email);
        String refreshToken = JwtUtil.createRefresh();

        // Refresh Token
        int updateCnt = userService.update(UserDto.builder().idx(idx).refreshToken(refreshToken).build());

        if (updateCnt == 1) {
            return AuthDto.builder()
                    .email(email)
                    .name(name)
                    .token(token)
                    .refreshToken(refreshToken).build();
        }
        return null;
    }

    public UserDto findByEmail(String email) {
        return userService.get(new UserDto(email));
    }

}
