package com.chu.customer.service;

import com.chu.customer.domain.*;
import com.chu.global.domain.*;

import java.util.List;

public interface CustomerService {
//
    // 아이디 중복체크
    boolean checkId(String id);

    // 이메일 중복체크
    boolean checkEmail(String email);

    // 고객 아이디 찾기
    ResponseFindIdDto findId(String name, String email);

    // 고객 회원가입
//    boolean signUp(RequestCustomerSignUpDto requestCustomerSignUpDto);
    void signUp(Customer customer);

    // 고객 비밀번호 찾기
    ResponseFindPwdDto findPwd(String name, String email, String id);

    // 고객 비밀번호 변경
    void changePwd(RequestCustomerChangePwdDto param);

    // 고객 알림 조회
    List<AlertCustomerOnLoginDto> getAlert(int customerSeq);

    
    // 고객 로그인
    //boolean signIn(RequestSignInDto requestSignInDto);
//
    // 로그인
    ResponseUserLoginToken signIn(RequestSignInDto requestSignInDto);

    // 로그인 후 메인페이지 정보
    ResponseCustomerLoginDetailDto getMainPageInfo(int customerSeq);

    // 고객 알림 읽음 처리
    void checkAlert(int alertSeq);
//
//    // 고객 정보 조회
//    Customer getCustomerInfo(String id);
//
//    // 고객 로그인 시 정보 조회
//    ResponseCustomerLoginDetailDto getLoginCustomerDetail(String id);
//

//
//    // 고객 유효성 체크
//    int isValidUser(RequestFindPwdDto requestFindPwdDto);
//
//    // 고객 비밀번호 변경
//    boolean changePwd(RequestChangePwdDto requestChangePwdDto);

    // 좋아요 상태 변경
    ResponseLikeDto changeLikeInfo(RequestLikeDto requestLikeDto);

//    // 알림 내역 조회
//    ArrayList<ResponseAlertCustomerDto> getAlertList(int customerSeq);
//
//    // 알림 생성
//    boolean createAlert(RequestAlertCreateDto requestAlertCreateDto);
//
//    // 알림 읽음여부 수정
//    boolean readAlert(RequestAlertReadDto requestAlertReadDto);
}
