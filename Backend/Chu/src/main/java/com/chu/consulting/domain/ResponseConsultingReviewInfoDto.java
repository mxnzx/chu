package com.chu.consulting.domain;

import com.chu.global.domain.HairStyleDto;
import com.chu.global.domain.ImageDto;
import lombok.Data;

import java.util.List;


@Data
public class ResponseConsultingReviewInfoDto {
    List<HairStyleDto> cutHairStyle;
    // 전체 펌 헤어스타일 리스트
    List<HairStyleDto> permHairStyle;
    List<ImageDto> imgs;
}
