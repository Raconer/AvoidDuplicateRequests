package com.duplicate.requests.avoid.api.reward;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duplicate.requests.avoid.api.reward.service.RewardService;
import com.duplicate.requests.avoid.api.sign.dto.SignDto;
import com.duplicate.requests.avoid.common.model.DefResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/reward")
@AllArgsConstructor
public class RewardController {

    RewardService rewardService;

    @PostMapping
    public ResponseEntity<?> rewardReq(@AuthenticationPrincipal SignDto signDto) {
        rewardService.request(signDto.getIdx());
        return ResponseEntity.ok(new DefResponse(HttpStatus.OK));
    }
}
