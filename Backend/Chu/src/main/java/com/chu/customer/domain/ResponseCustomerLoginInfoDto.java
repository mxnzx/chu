package com.chu.customer.domain;

import com.chu.global.domain.FaceDict;
import com.chu.global.domain.ImagePath;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseCustomerLoginInfoDto {
    int customerSeq;
    String name;
    String profileImg;
    int faceSeq;
    String faceLabel;

    public ResponseCustomerLoginInfoDto entityToDto(Customer customer){
        ResponseCustomerLoginInfoDto dto = new ResponseCustomerLoginInfoDto();

        dto.setCustomerSeq(customer.getSeq());
        dto.setName(customer.getName());

        if(customer.getImagePath() == null)
            dto.setProfileImg(null);
        else
            dto.setProfileImg(customer.getImagePath().getSavedImgName());



        dto.setFaceSeq(customer.getFaceDict().getSeq());
        dto.setFaceLabel(customer.getFaceDict().getFaceLabel());

        return dto;
    }
}
