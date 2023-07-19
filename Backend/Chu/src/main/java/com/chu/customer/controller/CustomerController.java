package com.chu.customer.controller;

import com.chu.customer.domain.CustomerDetailDto;
import com.chu.customer.domain.CustomerDto;
import com.chu.customer.service.CustomerService;
import com.chu.global.domain.ResponseDto;
import com.chu.global.domain.SignInDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    
    // 회원 가입
    @PostMapping(value = "/customer/sign-up")
    public ResponseEntity<ResponseDto> signUp(@RequestBody CustomerDto customerDto){
        log.info(customerDto.toString());
        int isSuccess = customerService.signUp(customerDto);

        if(isSuccess == 1){
            ResponseDto responseDto = new ResponseDto(200, null);
            return ResponseEntity.ok(responseDto);
        }
        else{
            ResponseDto responseDto = new ResponseDto(204, null);
            return ResponseEntity.ok(responseDto);
        }
    }

    // 로그인
    @GetMapping(value = "/customer/sign-in")
    public ResponseEntity<ResponseDto> signIn(@RequestParam String id, @RequestParam String pwd) {
        SignInDto signInDto = new SignInDto();
        signInDto.setId(id);
        signInDto.setPwd(pwd);

        boolean isUser = customerService.signIn(signInDto);

        // 로그인 성공
        if(isUser){
            CustomerDetailDto customerDetailDto = customerService.getCustomerDetail(signInDto.getId());
            ResponseDto responseDto = new ResponseDto(200, customerDetailDto);
            return ResponseEntity.ok(responseDto);
        }
        // 로그인 실패
        else{
            ResponseDto responseDto = new ResponseDto(204, null);
            return ResponseEntity.ok(responseDto);
        }
    }

}
