import { styled } from "styled-components";
import { motion } from "framer-motion";
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { BASE_URL } from '../../apis/rootUrl'
import { useQueryClient, useMutation } from "react-query";
import { toggleLikeButton } from "../../apis";
import Swal from 'sweetalert2';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100%;
  margin: 5px auto;
`;
const Hr = styled.div`
  margin: 20px 0 20px 0;
  border-bottom : 1.5px solid rgba(146, 132, 104, 0.231);
`;
const Wrap = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;
const Wrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-start;
  margin-left: 10px;
`;
const Box = styled.div`
  display: flex;
  align-items: center;
  margin: 5px 0;
`;
const DesignerImg = styled.img`
  width: 120px;
  height: 100%;
  border-radius: 0.2rem;
  cursor: pointer;
`;

const InfoBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-left: 20px;
`;
const LikeBox = styled.div`
  display: flex;
  flex-direction: column;
  margin-right: 20px;
  align-items: center;
`;
const Name = styled.span`
  font-size: 15px;
  font-weight: bold;
  margin-right: 10px;
  cursor: pointer;
`;
const StarBox = styled(motion.div)`
  width: 200px;
  height: 35px;
  display: flex;
  align-items: center;
`;
const Intro = styled.span`
  font-size: 13px;
  font-weight: 500;
  margin-top: 0px;
`;
const Reviewer = styled.span`
  font-size: 12px;
  font-weight: 500;
  color: grey;
  margin-top: 5px;
  margin-bottom: 5px;
  cursor: pointer;
`;
const HashTag = styled.span`
  font-size: 12px;
  font-weight: 700;
  padding: 5px 10px;
  margin-right: 5px;
  background-color: rgba(196, 192, 192, 0.5);
  border-radius: 5px;
  margin-top:3px;
`;
const Icon = styled.img`
  width: 18px;
  margin-right: 3px;
`;
const ReservBox = styled(motion.div)`
  width: 100px;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius:10px;
  padding: 5px;
  background-color: #e5e3dc;
`;
const HeartBox = styled.div`
  display: flex;
  align-items: center;
  padding-left: 90px;
`;
const Text = styled.span`
  font-size: 12px;
  font-weight: bold;
  text-align: center;
`;


const CostBox = styled.div`
display: flex;
justify-content: center;
align-items: center;
margin-right: 20px;
`;
const LikeBtn = styled.img`
  width: 27px;
  height: 27px;
  margin-right: 10px;
  cursor: pointer;
`;
function DesignerList(props) {
  const { data, sortOrder } = props;
  const userType = localStorage.getItem('userType');
  const customerSeq = localStorage.getItem("userSeq");
  const queryClient = useQueryClient();
  const navigate = useNavigate();


  //designerList를 키로 가진 query를 무효화 하여 새로운 데이터를 받아오게함
  const mutation = useMutation(toggleLikeButton, {
    onSuccess: () => {
      queryClient.invalidateQueries('designerList'); 
    },
  });

  const handleLikeClick = (designerSeq, currentLikeStatus) => {
    if (userType !== 'customer') {
      return;
    }
      const newLikeStatus = !currentLikeStatus; 
      mutation.mutate({designerSeq, customerSeq, isLike: newLikeStatus});
  };

  const handleReservBoxClick = (designerSeq) => {
    if (userType === 'designer') {
      Swal.fire({
        title: '알림',
        text: '예약서비스는 일반회원 전용 기능입니다.',
        icon: 'info',
        confirmButtonText: '확인'
      });
    } else if (!userType) {
      Swal.fire({
        title: '알림',
        text: '예약 서비스는 로그인 후 이용 가능합니다.',
        icon: 'info',
        showCancelButton: true,
        confirmButtonText: '로그인 하러가기',
        cancelButtonText: '현재 페이지로 돌아가기'
      }).then((result) => {
        if (result.isConfirmed) {
          navigate('/login');  // 로그인 페이지의 경로로 변경하세요.
        }
      });
    } else {
      navigate(`/reservation/${designerSeq}`);
    }
  };
  console.log(data, "리스트 데이터");
  // 필터에 따라 내림차순으로 정렬하는 함수
  const sortByLikeCnt = (designers) => {
    return designers.slice().sort((a, b) => b.likeCnt - a.likeCnt);
  };
  const sortByReviewScore = (designers) => {
    return designers.slice().sort((a, b) => b.reviewScore - a.reviewScore);
  };
  
  const sortByReviewCnt = (designers) => {
    return designers.slice().sort((a, b) => b.reviewCnt - a.reviewCnt);
  };
  // 정렬 기준에 따라 데이터를 정렬
  const sortedData = 
  sortOrder === "좋아요순" 
    ? sortByLikeCnt(data.designerList)
    : sortOrder === "평점순"
    ? sortByReviewScore(data.designerList)
    : sortOrder === "리뷰순"
    ? sortByReviewCnt(data.designerList)
    : data.designerList;
  
  // const [liked, setLiked] = useState(false); // 좋아요 상태를 state로 관리
  // const handleLikeClick = () => {
  //   setLiked((prevLiked) => !prevLiked); // 좋아요 상태를 토글
  // };

  return (
    <div>
    {sortedData.map((data) => (
    <Container key={data.designerSeq}>
      <Hr/>
      <Wrap>
      <Wrapper>
        <Box>
          <DesignerImg 
            src={`${BASE_URL}/designer-profile/${data.designerImg}`}
            // onClick={() => navigate(`/designerdetail/${item.designerSeq}`)}
            // src="/icon/designerimg.png"
            onClick={() => navigate(`/designerdetail/${data.designerSeq}`)}
          />
        </Box>
        <InfoBox>
          <StarBox>
            <Name 
              onClick={() => navigate(`/designerdetail/${data.designerSeq}`)}
            >{data.designerName} 디자이너
            </Name>
            <Icon src="/icon/star.png"/>
            <Text>{data.reviewScore.toFixed(1)}</Text>
          </StarBox>
          <Intro>{data.introduction}</Intro>
          <Reviewer>방문자 리뷰 {data.reviewCnt}</Reviewer>
          <Box>
            {
              data.hairStyleLabel.map((tag, index) => (
                <HashTag key={index} >#{tag}</HashTag>
              ))
            }
          </Box>
          <Box>
            <CostBox>
              <Icon src="/icon/money.png"/>
              <Text>{data.cost}</Text>
            </CostBox>
            <ReservBox
              onClick={() => handleReservBoxClick(data.designerSeq)}
              whileHover={{ backgroundColor: "rgb(237, 179, 99)" }}
              >
              {/* <Icon src="/icon/reservBtn.png" /> */}
              <Text>상담바로가기</Text>
            </ReservBox>
          </Box>
        </InfoBox>
      </Wrapper>
      <LikeBox>
        <HeartBox>
        {data.isLike ? (
          // 좋아요가 눌려있을 때 빨간색 하트 아이콘
          <LikeBtn src="/icon/hearto.png" onClick={() => handleLikeClick(data.designerSeq, data.isLike)}/>
        ) : (
          // 좋아요가 눌려있지 않을 때 빈 하트 아이콘
          <LikeBtn src="/icon/heartx.png" onClick={() => handleLikeClick(data.designerSeq, data.isLike)}/>
        )}
        <Text>{data.likeCnt}</Text>
        </HeartBox>
          
        </LikeBox>
      </Wrap>
    </Container>
    ))}
    </div>
  )
}
export default DesignerList;