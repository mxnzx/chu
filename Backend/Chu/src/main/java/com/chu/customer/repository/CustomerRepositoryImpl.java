package com.chu.customer.repository;

import com.chu.customer.domain.*;
import com.chu.global.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Slf4j
@Repository
@Transactional
public class CustomerRepositoryImpl{
//
//    @Override
//    public boolean checkId(String id) {
//        // 로직
//        // 아이디 중복이 있는지?
//
//        // 존재하지 않으면 가능 -> true
//        // 이미 존재하면 불가능 -> false
//        return false;
//    }
//
//    @Override
//    public boolean checkEmail(String email) {
//        // 로직
//        // 이메일 중복이 있는지?
//        return false;
//    }
//
//    @Override
//    public boolean signUp(RequestCustomerSignUpDto requestCustomerSignUpDto) {
//
//        // 로직
//        // 정상 가입인지
//
//        return false;
//    }
//
//    @Override
//    public boolean signIn(RequestSignInDto requestSignInDto) {
//        // 로직
//        // 조건에 맞는 사용자 존재하는지?
//        return false;
//    }
//
//    @Override
//    public Customer getCustomerInfo(String id) {
//
//        Customer customer = new Customer();
//        // 로직
//
//        // 아이디로 고객 테이블 정보 다 가져오기
//
//        return customer;
//    }
//
//    @Override
//    public ArrayList<ImageDto> getTopStyleByFace(int faceSeq) {
//        ArrayList<ImageDto> getTopStyleList = new ArrayList<>();
//        // 얼굴형 갖고 상담 결과 상세에서 탑 몇개 뽑기
//
//        // 헤어스타일 사전 거쳐서 헤어스타일 이미지 가져오기
//
//        return getTopStyleList;
//    }
//
//    @Override
//    public String findId(RequestFindIdDto requestFindIdDto) {
//        // 로직
//
//        // 이름, 이메일로 아이디 찾기
//        return null;
//    }
//
//    @Override
//    public int isValidUser(RequestFindPwdDto requestFindPwdDto) {
//        // 로직
//        // 이름, 아이디, 이메일로 존재하는 유저인지 확인
//        return 0;
//    }
//
//    @Override
//    public boolean changePwd(RequestChangePwdDto requestChangePwdDto) {
//        // 로직
//        // 고객 시퀀스넘버, 바꿀 비밀번호
//        return false;
//    }
//
//    @Override
//    public int changeLikeInfo(RequestLikeDto requestLikeDto) {
//        int likeCount = 0;
//
//        // 아직 고객, 디자이너 좋아요 관계 없다면 데이터 삽입
//
//        // 이미 고객, 디자이너 관계 있다면 상태만 변경
//
//        // 디자이너 좋아요 개수 반환
//        return likeCount;
//    }
//
//
//    @Override
//    public ArrayList<ResponseAlertCustomerDto> getAlertList(int customerSeq) {
//        ArrayList<ResponseAlertCustomerDto> alertList = new ArrayList<>();
//
//        // 로직
//
//        // 고객 번호로 알림 조회
//
//        return alertList;
//    }
//
//    @Override
//    public boolean createAlert(RequestAlertCreateDto requestAlertCreateDto) {
//        boolean isSuccess = true;
//        // 로직
//
//        // 알림 생성
//        return isSuccess;
//    }
//
//    @Override
//    public boolean readAlert(RequestAlertReadDto requestAlertReadDto) {
//        boolean isSuccess = true;
//
//        // 로직
//
//        // 알림 읽기
//
//        return isSuccess;
//    }
}
