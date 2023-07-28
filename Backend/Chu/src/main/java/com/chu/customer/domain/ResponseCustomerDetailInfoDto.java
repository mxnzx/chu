package com.chu.customer.domain;

import com.chu.global.domain.ResponseHairStyleDto;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ResponseCustomerDetailInfoDto {
    Customer customer;
    ArrayList<FaceDict> faceDictList;
    ArrayList<ResponseHairStyleDto> hairStyleList;
    ArrayList<CustomerHairConditionDto> hairConditionList;

}
