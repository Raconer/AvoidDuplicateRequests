package com.duplicate.requests.avoid.api.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duplicate.requests.avoid.api.user.dto.UserDto;
import com.duplicate.requests.avoid.api.user.service.UserService;
import com.duplicate.requests.avoid.common.model.DefDataResponse;
import com.duplicate.requests.avoid.common.model.DefResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    UserService userService;

    /* 테스트 용 사용자 리스트 */
    @GetMapping
    public ResponseEntity<?> getUserList() {
        List<UserDto> userList = this.userService.getList();
        if (userList != null) {
            return ResponseEntity.ok(new DefDataResponse(HttpStatus.OK, userList));
        }
        return ResponseEntity.ok(new DefResponse(HttpStatus.BAD_REQUEST));
    }
}
