package com.chu.worldcup.repository;

import com.chu.global.domain.ImageWithHairInfoDto;
import com.chu.worldcup.domain.WorldcupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Slf4j
@Repository
@RequiredArgsConstructor
public class WorldcupRepositoryImpl implements WorldcupRepository {
    // DB CONN

    @Override
    public int createWorldcup(WorldcupRequestDto worldcupRequestDto) {
        int resultRow = 0;

        // 로직

        // 월드컵 생성 후 삽입행 PK 반환

        return resultRow;
    }

    @Override
    public ArrayList<ImageWithHairInfoDto> getWorldcup(int worldcupSeq) {
        ArrayList<ImageWithHairInfoDto> resultList = new ArrayList<>();

        // 로직

        // 월드컵 시퀀스로 이미지들 다 가져오기

        return resultList;
    }
}