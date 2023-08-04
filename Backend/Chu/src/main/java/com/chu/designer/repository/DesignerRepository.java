package com.chu.designer.repository;

import com.chu.designer.domain.*;
import com.chu.global.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.ArrayList;

public interface DesignerRepository extends JpaRepository<Designer, Integer> {
    Designer getDesignerBySeq(int seq);
//    boolean checkId(String id);
//
//    boolean checkEmail(String email);
//
//    boolean signUp(RequestDesignerSignUpDto requestDesignerSignUpDto);
//
//    boolean signIn(RequestSignInDto requestSignInDto);
//
//    Designer getDesignerInfo(String id);
//
//    String findId(RequestFindIdDto requestFindIdDto);
//
//    int isValidUser(RequestFindPwdDto requestFindPwdDto);
//
//    boolean changePwd(RequestChangePwdDto requestChangePwdDto);
//
//    ArrayList<ResponseTimeStateDto> getTimeStateList(int designerSeq, Date date);
//
//    ArrayList<ResponseAlertDesignerDto> getAlertList(int designerSeq);
//
//    boolean createAlert(RequestAlertCreateDto requestAlertCreateDto);
//
//    boolean readAlert(RequestAlertReadDto requestAlertReadDto);
//
//    ArrayList<ResponseBestDesignerDto> getBestDesigners();
//

}
