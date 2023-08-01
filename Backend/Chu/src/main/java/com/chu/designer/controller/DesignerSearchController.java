package com.chu.designer.controller;

import com.chu.designer.domain.ResponseDesignerDetailInfoDto;
import com.chu.designer.domain.ResponseDesignerSearchAreaDto;
import com.chu.designer.domain.DesignerSearchDto;
import com.chu.designer.domain.ResponseDesignerSearchDto;
import com.chu.designer.service.DesignerSearchService;
import com.chu.global.domain.HttpResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/designer/search")
@RequiredArgsConstructor
public class DesignerSearchController {

    private final DesignerSearchService designerSearchService;
//    @GetMapping("/name")
//    public ResponseEntity<HttpResponseDto> search2Name(@RequestParam int customerSeq, @RequestParam String name){
//
//        List<DesignerSearchDto> designerSearchDtoList = designerSearchService.search2Name(customerSeq, name);
//
//        if(designerSearchDtoList.size() != 0){
//            ResponseDesignerSearchDto responseDesignerSearchDto = new ResponseDesignerSearchDto();
//            responseDesignerSearchDto.setDesignerListCnt(designerSearchDtoList.size());
//            responseDesignerSearchDto.setDesignerList(designerSearchDtoList);
//            HttpResponseDto httpResponseDto = new HttpResponseDto(200, responseDesignerSearchDto);
//            return ResponseEntity.ok(httpResponseDto);
//        }
//        else{
//            HttpResponseDto httpResponseDto = new HttpResponseDto(204, null);
//            return ResponseEntity.ok(httpResponseDto);
//        }
//    }
//
//    @GetMapping("/filter")
//    public ResponseEntity<HttpResponseDto> search2Filter(@RequestParam("hairStyle") String[] hairStyle, @RequestParam int customerSeq){
//        List<DesignerSearchDto> designerSearchDtoList = designerSearchService.search2Filter(customerSeq, hairStyle);
//
//        if(designerSearchDtoList.size() != 0){
//            ResponseDesignerSearchDto responseDesignerSearchDto = new ResponseDesignerSearchDto();
//            responseDesignerSearchDto.setDesignerListCnt(designerSearchDtoList.size());
//            responseDesignerSearchDto.setDesignerList(designerSearchDtoList);
//            HttpResponseDto httpResponseDto = new HttpResponseDto(200, responseDesignerSearchDto);
//            return ResponseEntity.ok(httpResponseDto);
//        }
//        else{
//            HttpResponseDto httpResponseDto = new HttpResponseDto(204, null);
//            return ResponseEntity.ok(httpResponseDto);
//        }
//    }

    @GetMapping("/review-score")
    public ResponseEntity<HttpResponseDto> search2ReviewScore(@RequestParam int customerSeq){
        List<DesignerSearchDto> designerSearchDtoList = designerSearchService.search2ReviewScore(customerSeq);

        if(designerSearchDtoList.size() != 0){
            ResponseDesignerSearchDto responseDesignerSearchDto = new ResponseDesignerSearchDto();
            responseDesignerSearchDto.setDesignerListCnt(designerSearchDtoList.size());
            responseDesignerSearchDto.setDesignerList(designerSearchDtoList);
            HttpResponseDto httpResponseDto = new HttpResponseDto(200, responseDesignerSearchDto);
            return ResponseEntity.ok(httpResponseDto);
        } else{
            HttpResponseDto httpResponseDto = new HttpResponseDto(204, null);
            return ResponseEntity.ok(httpResponseDto);
        }
    }

//    @GetMapping("/like-cnt")
//    public ResponseEntity<HttpResponseDto> search2LikeCount(@RequestParam int customerSeq){
//        List<DesignerSearchDto> designerSearchDtoList = designerSearchService.search2LikeCount(customerSeq);
//
//        if(designerSearchDtoList.size() != 0){
//            ResponseDesignerSearchDto responseDesignerSearchDto = new ResponseDesignerSearchDto();
//            responseDesignerSearchDto.setDesignerListCnt(designerSearchDtoList.size());
//            responseDesignerSearchDto.setDesignerList(designerSearchDtoList);
//            HttpResponseDto httpResponseDto = new HttpResponseDto(200, responseDesignerSearchDto);
//            return ResponseEntity.ok(httpResponseDto);
//        }
//        else{
//            HttpResponseDto httpResponseDto = new HttpResponseDto(204, null);
//            return ResponseEntity.ok(httpResponseDto);
//        }
//    }
//
//    @GetMapping("/around")
//    public ResponseEntity<HttpResponseDto> search2AllArea(){
//        List<ResponseDesignerSearchAreaDto> responseDesignerSearchAreaDtoList = designerSearchService.search2AllArea();
//
//        if(responseDesignerSearchAreaDtoList.size() != 0){
//            HttpResponseDto httpResponseDto = new HttpResponseDto(200, responseDesignerSearchAreaDtoList);
//            return ResponseEntity.ok(httpResponseDto);
//        }
//        else{
//            HttpResponseDto httpResponseDto = new HttpResponseDto(204, null);
//            return ResponseEntity.ok(httpResponseDto);
//        }
//    }
//
//    @GetMapping("/detail")
//    public ResponseEntity<HttpResponseDto> getDesignerDetailInfo(@PathVariable("designer-seq") int designerSeq, @RequestParam int customerSeq){
//
//        ResponseDesignerDetailInfoDto responseDesignerDetailInfoDto = designerSearchService.getDesignerDetailInfo(designerSeq, customerSeq);
//
//        if(responseDesignerDetailInfoDto != null){
//            HttpResponseDto httpResponseDto = new HttpResponseDto(200, responseDesignerDetailInfoDto);
//            return ResponseEntity.ok(httpResponseDto);
//        }
//        else{
//            HttpResponseDto httpResponseDto = new HttpResponseDto(204, null);
//            return ResponseEntity.ok(httpResponseDto);
//        }
//    }
}
