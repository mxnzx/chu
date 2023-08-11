// 고객의 최근 상담 내역 보여주는 컴포넌트

import { styled } from "styled-components";
import { AnimatePresence, motion, useScroll } from "framer-motion";
import { useMatch, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { useQuery } from "react-query";
import { getCustomerMyPage } from "../../apis";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 80%;
  margin: 0 auto;
  padding-top: 20px;
`;
const Hr = styled.div`
  /* margin: 20px 0 20px 0; */
  border-bottom : 2px solid rgba(0, 0, 0, 0.1);
`;
const Wrap = styled.div`
  display: flex;
  justify-content: space-between;
`;
const BigWrap = styled.div`
  display: flex;
  /* align-items: center; */
  flex-direction: column;
  margin-top: 20px;
`;
const Wrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-start;
  margin-left: 10px;
`;
const Box = styled.div`
  display: flex;
`;
const ReviewBox =styled.div`
  display: flex;
  flex-direction: column;
`;
const DetailBox = styled.div`
  margin-top: 40px;
  display: flex;
  justify-content: end;
`;
const DesignerImg = styled.img`
  width: 100px;
  margin-right: 10px;
`;

const ProfileBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 0 10px;
  align-items: center;
`;
const Name = styled.span`
  font-size: 13px;
  font-weight: bold;
`;

const Icon = styled.img`
  width: 21px;
  margin-right: 3px;
`;
const StarBox = styled(motion.div)`
  /* width: 70px; */
  height: 35px;
  display: flex;
  align-items: center;
`;
const Time = styled.span`
  margin-top: 10px;
  font-size: 14px;
  font-weight: bold;
  text-align: center;
`;
const Day = styled.span`
  margin-top: 10px;
  font-size: 14px;
  font-weight: bold;
  text-align: center;
`;
const ResultBtn = styled(motion.button)`
  font-size: 16px;
  font-weight: 500;
  padding: 5px 10px;
  border: none;
  /* background-color: #68655B; */
  border-radius: 5px;
  color: white;

`;
const Text = styled.span`
  font-size: 14px;
  /* font-weight: bold; */
  text-align: center;
  margin-right: 5px;
`;
const BoldText = styled.span`
  font-size: 14px;
  font-weight: bold;
  text-align: center;
`;
const CommentBox = styled.div`
display: flex;
/* justify-content: center; */
align-items: center;
margin: 20px 0px 10px 0px;
`;

const Overlay = styled(motion.div)`
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.6);
  /* opacity: 0; */
`;
const BigModal = styled(motion.div)`
  position: absolute;
  width: 40vw;
  height: 45vh;
  left: 0;
  right: 0;
  margin: 0 auto;
  border-radius: 15px;
  overflow: hidden;
  background-color: rgb(242,234,211);
`;
const InfoText = styled.span`
  font-size: 14px;
  margin-bottom: 10px;
  font-weight: bold;
`;
const InfoBox = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 10px;
`;
const BigModalBox = styled.div`
  display: flex;
  flex-direction: column;
  margin: 25px;
`;
const HashTag = styled(motion.span)`
  font-size: 12px;
  font-weight: 500;
  padding: 5px 10px;
  margin-right: 10px;
  margin-bottom: 20px;
  margin-top: 10px;
  background-color: rgba(175,149,113, 0.75);
  color: black;
  border-radius: 5px;
  cursor: pointer;
`;
const ResultWrap = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
`;

const ResultBox = styled.span`
  background-color: rgb(79, 71, 47);
  padding: 20px;
  border-radius: 10px;
  width: 70%;
  color: white;
  font-size: 14px;
  line-height: 1.5;
`;
const ResultHr = styled.hr`
  color: white;
`;
const ReviewImg = styled.img`
  width: 23%;
`;
const ResultBtnVariants = {
  nomal: {
    color: "white",
    backgroundColor: "#645D51",
  },
  hover: {
    color: "white",
    backgroundColor: "#F4991A",
  }
}
function ReserveList() {
  const customerSeq = localStorage.getItem("userSeq")
  console.log("커스터머 시퀀스",customerSeq);
  const { data, isLoading, isError } = useQuery(
    ["customerMyPage", customerSeq],
    () => getCustomerMyPage(customerSeq)
  );
  const {scrollY} = useScroll();
  const bigModalMatch = useMatch("customermypage/result/:consultingSeq");
  console.log(bigModalMatch)
  const navigate = useNavigate();
  const onBoxClicked = (consultingSeq) => {
    navigate(`result/${consultingSeq}`);
  };
  const onOverlayClick = () => {
    navigate(`/customermypage/${customerSeq}`);
  };
  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (isError) {
    return <div>An error occurred while fetching data.</div>;
  }

  return (
    <Container>
      <AnimatePresence>
      <Hr/>
      {data.responsePastConsultingDtoList.map((data) => (
        <BigWrap>
          <Wrap>
            <Wrapper>
              <Box>
                <ProfileBox>
                  {/* <DesignerImg src={data.designerImg}/> */}
                  <DesignerImg src= {`https://i9b111.q.ssafy.io/api/designer-profile/${data.designerImg}}`}/>
                  <StarBox>
                    <Icon src="/icon/star.png"/>
                    <BoldText>{data.allReviewScore}</BoldText>
                  </StarBox>
                </ProfileBox>
                <InfoBox>
                  <Name>{data.name} 디자이너</Name>
                  <Box>
                    <ReviewBox>
                    <CommentBox>
                      <Text>"{data.reviewContent}"</Text>
                    </CommentBox>
                    <StarBox>
                      <Text>나의 평점 </Text>
                      <Icon src="/icon/star.png"/>
                      <BoldText> {data.myReviewScore}</BoldText>
                    </StarBox>
                    </ReviewBox>
                  </Box>
                  {/* <DetailBox >
                    <ResultBtn 
                    onClick={() =>onBoxClicked(data.consultingSeq + "")}
                    layoutId={data.consultingSeq}
                    variants={ResultBtnVariants}
                    initial="nomal"
                    whileHover="hover"
                    >상담 결과 보기</ResultBtn>
                  </DetailBox> */}
                </InfoBox>
              </Box>
            </Wrapper>
            <Box>
              <ReviewBox>
                <Box>
              <Day>{data.consultingDate}({data.consultingDateDay })</Day>
              <Time> {data.consultingStartTime} ~ {data.consultingEndTime}</Time>
              </Box>
              <DetailBox >
                    <ResultBtn 
                    onClick={() =>onBoxClicked(data.consultingSeq + "")}
                    layoutId={data.consultingSeq}
                    variants={ResultBtnVariants}
                    initial="nomal"
                    whileHover="hover"
                    >상담 결과 보기</ResultBtn>
                  </DetailBox>
                  </ReviewBox>
            </Box>
          </Wrap>
          <Hr/>
        </BigWrap>
      ))}
      </AnimatePresence> 
      <AnimatePresence>
        { bigModalMatch ? (
          <>
            <Overlay 
              onClick={onOverlayClick}
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              exit={{ opacity: 0 }}
              />
              <BigModal 
                layoutId={bigModalMatch.params.consultingSeq}
                style={{ top: scrollY.get() + 110 }}
                initial={{ opacity: 0, y: "50%" }}
                animate={{ opacity: 1, y: "0%" }}
                exit={{ opacity: 0, y: "50%" }}
                transition={{ duration: 0.3}}>
                <BigModalBox>
                  <InfoBox>
                    <InfoText>상담사명 : {data.name} 디자이너</InfoText>
                    <InfoText>상담일시 : {data.consultingDate} {data.consultingStartTime}</InfoText>
                    <InfoText>스타일 진단 : {data.hairStyle.map((tag) => (
                      <HashTag
                      key={tag}
                      >
                      #{tag}
                    </HashTag>
                    ))}</InfoText>
                    <ResultWrap>
                      <ResultBox>
                        상담 결과 <br/><ResultHr/> {data.reviewResult}
                      </ResultBox>
                      <ReviewImg src="/icon/designerimg.png" />
                    </ResultWrap>
                  </InfoBox>
                </BigModalBox>
              </BigModal>          
          </>
          ) : null 
        }
      </AnimatePresence>
    </Container>
  )
}
export default ReserveList;