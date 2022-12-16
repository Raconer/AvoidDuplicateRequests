package com.duplicate.requests.avoid.api.reward.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RewardInfoDto extends RewardDto {
    private String yesterDate;
    private int rewardCnt;

    public RewardInfoDto(int userIdx, int rewardCnt) {
        super.setUserIdx(userIdx);
        super.setRegDate(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        this.yesterDate = sdf.format(cal.getTime());
        this.rewardCnt = rewardCnt;
    }
}
