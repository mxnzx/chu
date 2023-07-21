package com.chu.designer.service;

import com.chu.designer.domain.DesignerDto;
import com.chu.designer.domain.ResponseDesignerAreaInfo;
import com.chu.designer.domain.ResponseDesignerMyPageUpdateShowDto;
import com.chu.designer.domain.ResponseDesignerMyPageDto;
import com.chu.designer.repository.DesignerDetailRepository;
import com.chu.global.domain.ResponseHairStyleDto;
import com.chu.global.domain.ResponseHairStyleLabelDto;
import com.chu.global.domain.ResponsePermHairStyleDto;
import com.chu.global.domain.TimeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class DesignerDetailServiceImpl implements DesignerDetailService {
    private final DesignerDetailRepository designerDetailRepository;

    @Override
    public ResponseDesignerMyPageDto getMyPageInfo(int designerSeq) {
        ResponseDesignerMyPageDto responseDesignerMyPageDto = new ResponseDesignerMyPageDto();
        // 디자이너 정보
        DesignerDto designerDto = designerDetailRepository.getDesignerInfo(designerSeq);
        
        // 여기서 필요한거 뽑아쓰기

        // 디자이너가 잘하는 머리 스타일
        ArrayList<ResponseHairStyleLabelDto> responseHairStyleLabelDtoArrayList = designerDetailRepository.getHairStyleTag(designerSeq);

        // 디자이너가 그날 가능한 시간
        ArrayList<TimeDto> possibleTimeList = designerDetailRepository.getPossibleTimeList(designerSeq);


        return responseDesignerMyPageDto;
    }

    @Override
    public boolean patchIntroduction(int designerSeq, String introduction) {
        return designerDetailRepository.patchIntroduction(designerSeq, introduction);
    }

    @Override
    public boolean patchImg(int designerSeq, String img) {
        return designerDetailRepository.patchImg(designerSeq, img);
    }

    @Override
    public ResponseDesignerMyPageUpdateShowDto getDesignerMyPageUpdateInfo(int designerSeq) {
        ResponseDesignerMyPageUpdateShowDto responseDesignerMyPageUpdateShowDto = new ResponseDesignerMyPageUpdateShowDto();

        DesignerDto designerDto = designerDetailRepository.getDesignerInfo(designerSeq);

        ResponseDesignerAreaInfo designerAreaInfo = designerDetailRepository.getDesignerAreaInfo(designerSeq);

        ArrayList<ResponseHairStyleDto> allCutHairStyle = designerDetailRepository.getAllCutHairStyle();

        ArrayList<ResponsePermHairStyleDto> allPermHairStyle = designerDetailRepository.getAllPermHairStyle();

        ArrayList<ResponseHairStyleDto> myCutHairStyle = designerDetailRepository.getMyCutHairStyle(designerSeq);

        ArrayList<ResponsePermHairStyleDto> myPermHairStyle = designerDetailRepository.getMyPermHairStyle(designerSeq);

        return responseDesignerMyPageUpdateShowDto;
    }
}