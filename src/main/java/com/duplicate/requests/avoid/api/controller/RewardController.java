package com.duplicate.requests.avoid.api.controller;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duplicate.requests.avoid.common.model.DefDataResponse;
import com.duplicate.requests.avoid.common.model.DefResponse;
import com.duplicate.requests.avoid.dto.RewardUserDto;
import com.duplicate.requests.avoid.dto.SignDto;
import com.duplicate.requests.avoid.service.RewardService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/reward")
@AllArgsConstructor
public class RewardController {

    RewardService rewardService;

    // 보상 조회
    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam("regDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date regDate) {

        List<RewardUserDto> userDtos = this.rewardService.list(regDate);

        return ResponseEntity.ok(new DefDataResponse(HttpStatus.OK, userDtos));
    }

    // 보상 요청
    @PostMapping
    public ResponseEntity<?> rewardReq(@AuthenticationPrincipal SignDto signDto) {
        if (rewardService.request(signDto.getIdx()) == 1) {
            return ResponseEntity.ok(new DefResponse(HttpStatus.OK));
        }
        return ResponseEntity.ok(new DefResponse(HttpStatus.BAD_REQUEST));
    }
}
