package com.chu.designer.controller;

import com.chu.consulting.domain.ResponseConsultingDto;
import com.chu.designer.domain.*;
import com.chu.designer.service.DesignerDetailService;
import com.chu.global.domain.HttpResponseDto;
import com.chu.global.domain.ImageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/designer/detail")
@RequiredArgsConstructor
public class DesignerDetailController {

    private final DesignerDetailService designerDetailService;

    @GetMapping("/{designer-seq}")
    public ResponseEntity<HttpResponseDto> getDesignerDetailInfo(@PathVariable("designer-seq") int designerSeq) {

        ResponseDesignerMyPageUpdateShowDto responseDesignerMyPageUpdateShowDto = designerDetailService.getDesignerMyPageUpdateInfo(designerSeq);

        if (responseDesignerMyPageUpdateShowDto != null) {
            HttpResponseDto httpResponseDto = new HttpResponseDto(200, responseDesignerMyPageUpdateShowDto);
            return ResponseEntity.ok(httpResponseDto);
        } else {
            HttpResponseDto httpResponseDto = new HttpResponseDto(204, null);
            return ResponseEntity.ok(httpResponseDto);
        }
    }

    // 디자이너 회원 정보 수정
    @PutMapping("/{designer-seq}")
    public ResponseEntity<HttpResponseDto> updateDesignerInfo(@PathVariable("designer-seq") int designerSeq,
                                                              @RequestBody RequestDesignerInfoUpdateDto requestDesignerInfoUpdateDto) {

//        boolean isSuccess = designerDetailService.updateDesignerInfo(designerSeq, requestDesignerInfoUpdateDto);
//
//        if (isSuccess) {
//            HttpResponseDto httpResponseDto = new HttpResponseDto(200, null);
//            return ResponseEntity.ok(httpResponseDto);
//        } else {
//            HttpResponseDto httpResponseDto = new HttpResponseDto(204, null);
//            return ResponseEntity.ok(httpResponseDto);
//        }
        try{
            designerDetailService.updateDesignerInfo(designerSeq, requestDesignerInfoUpdateDto);
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new HttpResponseDto(HttpStatus.NO_CONTENT.value(), null));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new HttpResponseDto(HttpStatus.OK.value(), null));
    }

    @GetMapping("/mypage/{designer-seq}")
    public ResponseEntity<HttpResponseDto> getMyPageInfo(@PathVariable("designer-seq") int designerSeq) {
        ResponseDesignerMyPageDto responseDesignerMyPageDto = designerDetailService.getMyPageInfo(designerSeq);

        if (responseDesignerMyPageDto != null) {
            HttpResponseDto httpResponseDto = new HttpResponseDto(200, responseDesignerMyPageDto);
            return ResponseEntity.ok(httpResponseDto);
        } else {
            HttpResponseDto httpResponseDto = new HttpResponseDto(204, null);
            return ResponseEntity.ok(httpResponseDto);
        }
    }

    @PatchMapping("/introduction/{designer-seq}")
    public ResponseEntity<HttpResponseDto> patchIntroduction(@PathVariable("designer-seq") int designerSeq,
                                                             @RequestParam String introduction) throws JsonProcessingException {

        boolean isSuccess = designerDetailService.patchIntroduction(designerSeq, introduction);

        if (isSuccess) {
            // Introduction 객체를 Map<String, Object> 형태로 변환
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("introduction", introduction);

            // HttpResponseDto 객체 생성
            HttpResponseDto httpResponseDto = new HttpResponseDto(200, dataMap);

            return ResponseEntity.ok(httpResponseDto);
        } else {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("introduction", null);

            HttpResponseDto httpResponseDto = new HttpResponseDto(204, dataMap);
            return ResponseEntity.ok(httpResponseDto);
        }
    }

    @PostMapping("/img/{designer-seq}")
    public ResponseEntity<HttpResponseDto> patchImg(@PathVariable("designer-seq") int designerSeq, @RequestPart("img") MultipartFile file) throws IOException {

        // 여기서 디비에 폴더경로 가져오기, 실제 파일 서버 저장 함수
        String filePath = designerDetailService.getSavedImgFilePathDesignerProfile(file);

        // 여기서 디비에 실제 파일 이름를 가져오는거
        String uploadFileName = designerDetailService.getUploadImgFilePath(file);

        boolean isSuccess = designerDetailService.patchImg(designerSeq, uploadFileName);

        if (isSuccess) {
            HttpResponseDto httpResponseDto = new HttpResponseDto(200, uploadFileName);
            return ResponseEntity.ok(httpResponseDto);
        } else {
            HttpResponseDto httpResponseDto = new HttpResponseDto(204, null);
            return ResponseEntity.ok(httpResponseDto);
        }
    }
//
//    @GetMapping("/time")
//    public ResponseEntity<HttpResponseDto> getPossibleReservationTime(@PathVariable("designer-seq") int designerSeq, Date date) {
//
//        ArrayList<TimeDto> possibleReservationTime = designerDetailService.getPossibleReservationTime(designerSeq, date);
//
//        if (possibleReservationTime.size() != 0) {
//            HttpResponseDto httpResponseDto = new HttpResponseDto(200, possibleReservationTime);
//            return ResponseEntity.ok(httpResponseDto);
//        } else {
//            HttpResponseDto httpResponseDto = new HttpResponseDto(204, null);
//            return ResponseEntity.ok(httpResponseDto);
//        }
//    }
//
//    @PutMapping("/time")
//    public ResponseEntity<HttpResponseDto> updatePossibleReservationTime(@PathVariable("designer-seq") int designerSeq, @RequestBody RequestReservationPossibleDateAndTimeDto requestReservationPossibleDateAndTimeDto) {
//
//        boolean isSuccess = designerDetailService.updatePossibleReservationTime(designerSeq, requestReservationPossibleDateAndTimeDto);
//
//        if (isSuccess) {
//            HttpResponseDto httpResponseDto = new HttpResponseDto(200, null);
//            return ResponseEntity.ok(httpResponseDto);
//        } else {
//            HttpResponseDto httpResponseDto = new HttpResponseDto(204, null);
//            return ResponseEntity.ok(httpResponseDto);
//        }
//    }
//
//    @GetMapping("/reservation-list")
//    public ResponseEntity<HttpResponseDto> getReservationList(@PathVariable("designer-seq") int designerSeq) {
//
//        ArrayList<ResponseConsultingDto> reservationList = designerDetailService.getReservationList(designerSeq);
//
//        if (reservationList.size() != 0) {
//            HttpResponseDto httpResponseDto = new HttpResponseDto(200, reservationList);
//            return ResponseEntity.ok(httpResponseDto);
//        } else {
//            HttpResponseDto httpResponseDto = new HttpResponseDto(204, null);
//            return ResponseEntity.ok(httpResponseDto);
//        }
//    }

    @GetMapping("/portfolio/{designer-seq}")
    public ResponseEntity<HttpResponseDto> getPortfolio(@PathVariable("designer-seq") int designerSeq) {

        List<ImageDto> portfolioList = new ArrayList<>();

        try{
            portfolioList = designerDetailService.getPortfolio(designerSeq);
        } catch (Exception e){
            e.printStackTrace();
            HttpResponseDto httpResponseDto = new HttpResponseDto(204, null);
            return ResponseEntity.ok(httpResponseDto);
        }

        Map<String, List<ImageDto>> result = new HashMap<>();
        result.put("imgs", portfolioList);

        HttpResponseDto httpResponseDto = new HttpResponseDto(200, result);
        return ResponseEntity.ok(httpResponseDto);
    }

    @PostMapping("/portfolio/{designer-seq}")
    public ResponseEntity<HttpResponseDto> postPortfolio(@PathVariable("designer-seq") int designerSeq, @RequestPart("img") MultipartFile file) {

        int portfolioSeq = -1;

        try{
            // 포트폴리오 삽입하고 행 SEQ 가져오기
            portfolioSeq = designerDetailService.firstPostPortfolioImage(designerSeq);

            // 여기서 디비에 폴더경로 가져오기, 실제 파일 서버 저장 함수
            String fileName = designerDetailService.getSavedImgFilePath(file, portfolioSeq);
            
            // 여기서 디비에 폴더경로, 실제파일 이름 저장 그리고 이미지 시퀀스 반환
            designerDetailService.postPortfolioImage(portfolioSeq, fileName);
        } catch (Exception e){
            e.printStackTrace();
            HttpResponseDto httpResponseDto = new HttpResponseDto(204, null);
            return ResponseEntity.ok(httpResponseDto);
        }

        HttpResponseDto httpResponseDto = new HttpResponseDto(200, portfolioSeq);
        return ResponseEntity.ok(httpResponseDto);
    }

    @DeleteMapping("/portfolio/{designer-seq}")
    public ResponseEntity<HttpResponseDto> deletePortfolio(@PathVariable("designer-seq") int designerSeq, @RequestParam int imageSeq) {

        try{
//            designerDetailService.deletePortfolioImage(designerSeq, imageSeq);
            designerDetailService.deletePortfolioImage(imageSeq);
        } catch (Exception e){
            e.printStackTrace();
            HttpResponseDto httpResponseDto = new HttpResponseDto(204, null);
            return ResponseEntity.ok(httpResponseDto);
        }
        HttpResponseDto httpResponseDto = new HttpResponseDto(200, null);
        return ResponseEntity.ok(httpResponseDto);
    }

    // 디자이너 예약 목록 조회
    @GetMapping("/reservation-list/{designer-seq}")
    public ResponseEntity<HttpResponseDto> getReservationList(@PathVariable("designer-seq") int designerSeq){

        List<ResponseConsultingDto> response = null;

        try{
            response = designerDetailService.getReservationList(designerSeq);
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new HttpResponseDto(HttpStatus.NO_CONTENT.value(), null));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new HttpResponseDto(HttpStatus.OK.value(), response));
    }

    //    @PutMapping("/time")
//    public ResponseEntity<HttpResponseDto> updatePossibleReservationTime(@PathVariable("designer-seq") int designerSeq, @RequestBody RequestReservationPossibleDateAndTimeDto requestReservationPossibleDateAndTimeDto) {
//
//        boolean isSuccess = designerDetailService.updatePossibleReservationTime(designerSeq, requestReservationPossibleDateAndTimeDto);
//
//        if (isSuccess) {
//            HttpResponseDto httpResponseDto = new HttpResponseDto(200, null);
//            return ResponseEntity.ok(httpResponseDto);
//        } else {
//            HttpResponseDto httpResponseDto = new HttpResponseDto(204, null);
//            return ResponseEntity.ok(httpResponseDto);
//        }
//    }

    // 디자이너 상담 예약 가능 시간 수정
    @PostMapping("/time/{designer-seq}")
    public ResponseEntity<HttpResponseDto> updatePossibleRsvTime(@PathVariable("designer-seq") int designerSeq,
                                                                 @RequestBody RequestUpdatePossibleRsvTime requestUpdatePossibleRsvTime){

        try{
            designerDetailService.updatePossibleRsvTime(designerSeq, requestUpdatePossibleRsvTime);
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new HttpResponseDto(HttpStatus.NO_CONTENT.value(), null));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new HttpResponseDto(HttpStatus.OK.value(), null));
    }
}
