package com.chu.designer.service;

import com.chu.consulting.domain.Consulting;
import com.chu.consulting.domain.ConsultingVirtualImg;
import com.chu.consulting.domain.ResponseConsultingDto;
import com.chu.consulting.repository.ConsultingRepository;
import com.chu.consulting.repository.ConsultingVirtualImgRepository;
import com.chu.customer.domain.Customer;
import com.chu.customer.domain.CustomerHairCondition;
import com.chu.customer.repository.CustomerHairConditionRepository;
import com.chu.customer.repository.CustomerRepository;
import com.chu.designer.domain.*;
import com.chu.designer.repository.DesignerRepository;
import com.chu.designer.repository.ReservationAvailableSlotRepository;
import com.chu.global.repository.DesignerTagInfoRepository;
import com.chu.designer.repository.DesignerDetailRepository;
import com.chu.designer.repository.DesignerRepository;
import com.chu.global.domain.*;
import com.chu.global.repository.FaceDictRepository;
import com.chu.global.repository.HairStyleDictRepository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import com.mashape.unirest.http.Unirest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

import static java.util.stream.IntStream.builder;

@Slf4j
@Service
@RequiredArgsConstructor
public class DesignerDetailServiceImpl implements DesignerDetailService {

    private final DesignerSearchService designerSearchService;
    private final DesignerDetailRepository designerDetailRepository;
    private final DesignerRepository designerRepository;
    private final DesignerTagInfoRepository designerTagInfoRepository;
    private final ReservationAvailableSlotRepository reservationAvailableSlotRepository;
    private final HairStyleDictRepository hairStyleDictRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final ConsultingRepository consultingRepository;
    private final CustomerRepository customerRepository;
    private final FaceDictRepository faceDictRepository;
    private final CustomerHairConditionRepository customerHairConditionRepository;
    private final ConsultingVirtualImgRepository consultingVirtualImgRepository;


    @Override
    public String getSavedImgFilePath(MultipartFile file, int portfolioSeq) throws IOException {
        String uploadDir = "/chu/upload/images/designer/portfolio/";

//        png로 다 저장할껀데 안되는 거 있으면 이름 꺼내서 구분자 기준으로 왼쪽 지우고 번호 넣기로 다시 구현
//        String fileName = file.getOriginalFilename();
        String fileName = "p_" + portfolioSeq + ".png";

        File directory = new File(uploadDir);
        String filePath = uploadDir + fileName;

        File destFile = new File(filePath);
        System.out.println(filePath);

        if (!directory.exists()) {
            boolean mkdirsResult = directory.mkdirs();
            if (mkdirsResult) {
                System.out.println("디렉토리 생성 성공");
            } else {
                System.out.println("디렉토리 생성 실패");
            }
        }

        file.transferTo(destFile);
        log.info("서비스 >>> 파일 저장 성공! filePath : " + filePath);
        return fileName;
    }

    @Override
    public int firstPostPortfolioImage(int designerSeq) {
        Designer designer = designerRepository.getDesignerBySeq(designerSeq);

        DesignerPortfolio designerPortfolio = new DesignerPortfolio(designer);
        designerPortfolio.setCreatedDate(LocalDateTime.now());

        int imgSeq = -1;

        try{
            DesignerPortfolio designerPortfolioReturn = designerDetailRepository.save(designerPortfolio);
            imgSeq = designerPortfolioReturn.getSeq();
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }

        return imgSeq;
    }

    @Override
    public String getSavedImgFilePathDesignerProfile(MultipartFile file) throws IOException {
        String uploadDir = "/chu/upload/images/designer/";
        String fileName = file.getOriginalFilename();

        File directory = new File(uploadDir);
        String filePath = uploadDir + fileName;

        File destFile = new File(filePath);
        System.out.println(fileName);

        if (!directory.exists()) {
            boolean mkdirsResult = directory.mkdirs();
            if (mkdirsResult) {
                System.out.println("디렉토리 생성 성공");
            } else {
                System.out.println("디렉토리 생성 실패");
            }
        }

        file.transferTo(destFile);
        return fileName;
    }

    @Override
    public String getUploadImgFilePath(MultipartFile file) throws IOException {
        String uploadName = file.getOriginalFilename();
        return uploadName;
    }

    @Override
    @Transactional
    public void postPortfolioImage(int portfolioSeq, String imgName) {

        DesignerPortfolio designerPortfolio = designerDetailRepository.findDesignerPortfolioBySeq(portfolioSeq);
        ImagePath imagePath = new ImagePath();
        imagePath.setSavedImgName(imgName);
        imagePath.setUploadImgName(imgName);
        designerPortfolio.setImagePath(imagePath);
    }



    @Override
    public List<ImageDto> getPortfolio(int designerSeq) {

        List<ImageDto> imgList =new ArrayList<>();

        try{
            List<DesignerPortfolio> designerPortfolios = designerDetailRepository.getPortfolioByDesignerPortfolio(designerSeq);

            for (int i = 0; i < designerPortfolios.size(); i++) {
                ImageDto imgDto = new ImageDto();
                imgDto.setImgSeq(designerPortfolios.get(i).getSeq());
                imgDto.setImgName(designerPortfolios.get(i).getImagePath().getUploadImgName());
                imgList.add(imgDto);
            }

            return imgList;
        } catch (Exception e){

            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public boolean deletePortfolioImage(int imageSeq) {
        try{
            designerDetailRepository.deleteBySeq(imageSeq);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ResponseDesignerMyPageDto getMyPageInfo(int designerSeq) {

        // 디자이너 정보
        Designer designer = designerRepository.getDesignerBySeq(designerSeq);
        if(designer == null) return null;

        // 디자이너가 잘하는 머리 스타일
        List<String> designerTags = new ArrayList<>();
        for( DesignerTagInfo dti : designerTagInfoRepository.findByDesignerSeqWithHairStyleDict(designerSeq)) {
            designerTags.add(dti.getHairStyleDict() != null ? dti.getHairStyleDict().getHairStyleLabel() : null);
        }

        // 디자이너가 그날 가능한 시간
        List<String> possibleTimes = reservationAvailableSlotRepository.findAvailableTimeByDesignerSeq(designerSeq);

        ResponseDesignerMyPageDto result = ResponseDesignerMyPageDto.builder()
                .name(designer.getName())
                .cost(designer.getCost())
                .email(designer.getEmail())
                .introduction(designer.getIntroduction())
                .img(designer.getImagePath() != null ? designer.getImagePath().getUploadImgName() : null)
                .hairStyleTag(designerTags)
                .selectTime(possibleTimes)
                .build();

        return result;
    }

    @Override
    @Transactional
    public boolean patchIntroduction(int designerSeq, String introduction) {
        Designer designer = designerRepository.getDesignerBySeq(designerSeq);
        if(designer==null) return false;
        designer.setIntroduction(introduction);

        return true;
    }

    //
    @Override
    @Transactional
    public boolean patchImg(int designerSeq, String fileName) {

        Designer designer = designerRepository.getDesignerBySeq(designerSeq);

        ImagePath imagePath = new ImagePath();
        imagePath.setUploadImgName(fileName);
        imagePath.setSavedImgName(fileName);
        // 저장
        designer.setImagePath(imagePath);
        return true;
    }

    @Override
    public ResponseDesignerMyPageUpdateShowDto getDesignerMyPageUpdateInfo(int designerSeq) {

        ResponseDesignerMyPageUpdateShowDto result = null;

        try {
            // 디자이너 관련 필드에 필요한 데이터
            Designer designer = designerRepository.getDesignerBySeq(designerSeq);

            // 전체 헤어스타일 태그 데이터
            List<HairStyleDto> allCutHairStyle = designerSearchService.showCategoryView(1);
            List<HairStyleDto> allPermHairStyle = designerSearchService.showCategoryView(2);

            // 해당 디자이너의 헤어스타일 태그 시퀀스
            List<Integer> myHairStyleSeq = new ArrayList<>();
            for (DesignerTagInfo dti : designerTagInfoRepository.findByDesignerSeq(designerSeq)) {
                myHairStyleSeq.add(dti.getHairStyleDict() != null ? dti.getHairStyleDict().getSeq() : null);   //1,2,5
            }

            // 해당 헤어스타일 시퀀스 하나와 헤어스타일 사전을 돌면서 해당 카테고리 데이터만 가져오기. 객체를 Map 변환
            Map<String, Object> myCutHairStyleMap = new HashMap<>();
            myCutHairStyleMap.put("hairStyleSeq", hairStyleDictRepository.findByMyHairStyleCategorySeq(1, myHairStyleSeq));

            Map<String, Object> myPermHairStyleMap = new HashMap<>();
            myPermHairStyleMap.put("hairStyleSeq", hairStyleDictRepository.findByMyHairStyleCategorySeq(2, myHairStyleSeq));

            result = ResponseDesignerMyPageUpdateShowDto.builder()
                    .name(designer.getName())
                    .id(designer.getId())
                    .email(designer.getEmail())
                    .cost(designer.getCost())
                    .certificationNum(designer.getCertificationNum())
                    .salonName(designer.getSalonName())
                    .latitude(designer.getLatitude())
                    .longitude(designer.getLongitude())
                    .introduction(designer.getIntroduction())
                    .address(designer.getAddress())
                    .allCutHairStyle(allCutHairStyle)
                    .allPermHairStyle(allPermHairStyle)
                    .myCutHairStyle(myCutHairStyleMap)
                    .myPermHairStyle(myPermHairStyleMap)
                    .build();
        } catch (Exception e) {
            return null;
        }

        return result;
    }

    @Override
    @Transactional
    public void updateDesignerInfo(int designerSeq, RequestDesignerInfoUpdateDto updateDto) {

        try{
            Designer designer = designerRepository.getDesignerBySeq(designerSeq);
            designer.setCost(updateDto.getCost());
            designer.setPwd(updateDto.getPwd());
            designer.hashPassword(bCryptPasswordEncoder);    // 비밀번호 암호화
            designer.setSalonName(updateDto.getSalonName());
//            designer.setLatitude(updateDto.getLatitude());
//            designer.setLongitude(updateDto.getLongitude());

//            Double[] coordinate = getLatLongData(updateDto.getAddress());
//            designer.setLatitude(coordinate[0]);
//            designer.setLongitude(coordinate[1]);

            //designer.setAddress(updateDto.getAddress());
            designer.setIntroduction(updateDto.getIntroduction());

            KakaoGeoRes bodyJson = getLatLongData(updateDto.getAddress());
            Double longitude = bodyJson.getDocuments().get(0).getX();
            Double latitude = bodyJson.getDocuments().get(0).getY();

            designer.setLongitude(longitude);
            designer.setLatitude(latitude);
            String address = bodyJson.getDocuments().get(0).getRoad_address().get("address_name").toString();
//            System.out.println(">>>>>"+address);
//            System.out.println(latitude + " " + longitude);
            designer.setAddress(address);

            // 해당 디자이너가 들어있는 데이터를 전부 삭제한다
            designerTagInfoRepository.deleteByDesignerSeq(designerSeq);

            // 새로 받은 값들을 해당 디자이너와 함께 데이터를 추가한다
            for(Integer tagSeq : updateDto.getMyHairStyleTag()) {
                DesignerTagInfo dti = new DesignerTagInfo();
                dti.setDesigner(designer);

                dti.setCreatedTime(LocalDateTime.now());
                HairStyleDict hairStyleDict = hairStyleDictRepository.findBySeq(tagSeq);
                dti.setHairStyleDict(hairStyleDict);

                designerTagInfoRepository.save(dti);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public KakaoGeoRes getLatLongData(String roadFullAddr){
        String APIKey = "KakaoAK 69be6d9c142a03f5304472265767af1f";

        HashMap<String, Object> map = new HashMap<>(); //결과를 담을 map
        KakaoGeoRes bodyJson = null;

        try {
            String apiURL = "https://dapi.kakao.com/v2/local/search/address.json?query="
                    + URLEncoder.encode(roadFullAddr, "UTF-8");

            HttpResponse<JsonNode> response = Unirest.get(apiURL)
                    .header("Authorization", APIKey)
                    .asJson();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

            bodyJson = objectMapper.readValue(response.getBody().toString(), KakaoGeoRes.class);



        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        return bodyJson;
    }

    @Override
    @Transactional
    public void updatePossibleRsvTime(int designerSeq, RequestUpdatePossibleRsvTime requestUpdatePossibleRsvTime) {

        try{
            Map<String, List<String>> map = requestUpdatePossibleRsvTime.getDateAndTimes();

            if(map != null && !map.isEmpty()){
                for(Map.Entry<String, List<String>> entrySet : map.entrySet()){

                    String date = entrySet.getKey();;

                    // reservation_available_slot 테이블에서 기존 날짜 삭제하기
                    reservationAvailableSlotRepository.deleteAllByDesignerSeqAndDate(designerSeq, date);

                    // 새로운 날짜 추가하기
                    List<String> times = entrySet.getValue();

                    for(String time : times){
                        ReservationAvailableSlot dto = new ReservationAvailableSlot();

                        dto.setDate(date);
                        dto.setTime(time);
                        dto.setState('P');
                        dto.setCreatedDate(LocalDateTime.now());

                        Designer d = designerRepository.getDesignerBySeq(designerSeq);
                        dto.setDesigner(d);

                        reservationAvailableSlotRepository.save(dto);
                    }
                }
            }

//            for(RsvDateAndTimes dat : list){
//
//                String date = dat.getDate();
//
//
//                // reservation_available_slot 테이블에서 기존 날짜 삭제하기
//                reservationAvailableSlotRepository.deleteAllByDesignerSeqAndDate(designerSeq, date);
//
//                // 새로운 날짜 추가하기
//                List<String> times = dat.getTimes();
//
//                for(String time : times){
//                    ReservationAvailableSlot dto = new ReservationAvailableSlot();
//
//                    dto.setDate(date);
//                    dto.setTime(time);
//                    dto.setState('P');
//                    dto.setCreatedDate(LocalDateTime.now());
//
//                    Designer d = designerRepository.getDesignerBySeq(designerSeq);
//                    dto.setDesigner(d);
//
//                    reservationAvailableSlotRepository.save(dto);
//                }
//
//            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<ResponseConsultingDto> getReservationList(int designerSeq) {

        List<ResponseConsultingDto> response = new ArrayList<>();

        List<Consulting> consultingList =consultingRepository.findByDesignerSeq(designerSeq);

        for(Consulting c : consultingList){
            ResponseConsultingDto dto = new ResponseConsultingDto();

            dto.setConsultingSeq(c.getSeq());
            dto.setConsultingDate(c.getConsultingDate().getDate());
            dto.setConsultingMemo(c.getMemo());
            dto.setOriginImg(c.getImagePath().getUploadImgName());
//            dto.setCancelDate(c.getCancelDate().toString());

            String cancelDate = null;
            if(c.getCancelDate() == null)
                cancelDate = null;
            else cancelDate = c.getCancelDate().toString();
            dto.setCancelDate(cancelDate);


            Customer customer = customerRepository.getCustomerBySeq(c.getCustomer().getSeq());

            dto.setName(customer.getName());
            dto.setGender(customer.getGender());

            FaceDict faceDict = faceDictRepository.findBySeq(customer.getFaceDict().getSeq());

            dto.setFaceLabel(faceDict.getFaceLabel());


            // hair condition setting
            List<String> hairCondition = new ArrayList<>();

            List<CustomerHairCondition> chc = customerHairConditionRepository.findAllByCustomerSeq(customer.getSeq());
            for(CustomerHairCondition customerHairCondition : chc){
                hairCondition.add(customerHairCondition.getHairConditionDict().getLabel());
            }

            dto.setHairCondition(hairCondition);


            // virtualImg setting
            List<ConsultingVirtualImg> consultingVirtualImgs = new ArrayList<>();
            consultingVirtualImgs = consultingVirtualImgRepository.findAllByConsultingSeq(c.getSeq());

            List<String> virtualImg = new ArrayList<>();

            for(ConsultingVirtualImg cv : consultingVirtualImgs){
                virtualImg.add(cv.getImagePath().getSavedImgName());
            }

            dto.setVirtualImg(virtualImg);


            // time setting
            dto.setTime(c.getConsultingDate().getTime());



            response.add(dto);
        }

        return response;
    }
//
//    @Override
//    public boolean updatePossibleReservationTime(int designerSeq, RequestReservationPossibleDateAndTimeDto requestReservationPossibleDateAndTimeDto) {
////        1. 기존에 있던 해당 날짜 데이터 전부 삭제
//        boolean deleteSuccess = designerDetailRepository.deleteAlreadyPossibleTime(designerSeq, requestReservationPossibleDateAndTimeDto);
////        2. 새로 들어온 데이터 전부 삽입
//
//        boolean postSuccess = designerDetailRepository.postPossibleTime(designerSeq, requestReservationPossibleDateAndTimeDto);
//
//        if(deleteSuccess && postSuccess){
//            return true;
//        }
//        else{
//            return false;
//        }
//    }
//
//    @Override
//    public ArrayList<TimeDto> getPossibleReservationTime(int designerSeq, Date date) {
//        return designerDetailRepository.getPossibleReservationTime(designerSeq, date);
//    }
//
//    @Override
//    public ArrayList<ResponseConsultingDto> getReservationList(int designerSeq) {
//
//        ArrayList<ResponseConsultingDto> resultList = designerDetailRepository.getReservationList(designerSeq);
//
//        // 조인이 많아질 것 같으면 함수 나누기
//
//        // 갖고 온 데이터 기반으로 그 상담의 합성 얼굴 사진 뽑아오기
//
////        designerDetailRepository.getConfusionImages(consultingSeq);
//
//        return resultList;
//    }
//
//    @Override
//    public boolean postPortfolioImage(int designerSeq, String img) {
//        return designerDetailRepository.postPortfolioImage(designerSeq, img);
//    }
//
//    @Override
//    public boolean deletePortfolioImage(int designerSeq, int imageSeq) {
//        return designerDetailRepository.deletePortfolioImage(designerSeq, imageSeq);
//    }
}
