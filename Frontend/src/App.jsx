import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { OpenVidu } from 'openvidu-browser';
import Header from "./components/Common/Header";
import Home from "./Routes/Home";
import DesignerDetail from "./Routes/DesignerPage/DesignerDetail";
import ViduRoom from "./components/OpenVidu/ViduRoom";
import CustomerSignUp from "./Routes/SignUpPage/CutomerSignUp";
import DesignerSignUp from "./Routes/SignUpPage/DesignerSignUp";
import Complete from "./Routes/SignUpPage/Complete";
import LogIn from "./Routes/LogInPage/LogIn";
import FindId from "./Routes/LogInPage/FindId";
import FoundId from "./Routes/LogInPage/FoundId";
import AuthNum from "./Routes/LogInPage/AuthNum";
import UserType from "./Routes/SignUpPage/UserType";
import FindPw from "./Routes/LogInPage/FindPw";
import ChangePw from "./Routes/LogInPage/ChangePw";
import WorldcupImgUpload from "./Routes/WorldCupPage/WorldcupImgUpload";
import CustomerMyPage from "./Routes/CustomerPage/CustomerMyPage";
import DesignerMyPage from "./Routes/DesignerPage/DesignerMyPage";
import ListView from "./Routes/ViewPage/ListView";
import Survey from "./components/ModalComponent/Survey";
import EditDesignerInfo from "./Routes/DesignerPage/EditDesignerInfo";
import EditCustomerInfo from "./Routes/CustomerPage/EditCustomerInfo";
import Reservation from "./Routes/DesignerPage/Reservation";
import CheckReserve from "./Routes/KakaoPage/CheckReserve";
import PaySuccess from "./Routes/KakaoPage/PaySuccess";
import ConsultResultPage from "./Routes/ConsultPage/ConsultResultPage";
import ViduRoomWrapper from "./components/OpenVidu/ViduRoomWrapper";
import LikeDesigner from "./components/CustomerComponent/LikeDesigner";
import MapView from "./Routes/ViewPage/MapView";
import WorldCupRoomWrapper from "./components/WorldCup/WorldCupRoomWrapper";
import Event from "./Routes/EventPage/Event";
import Map from "./Routes/ViewPage/Map";
import Footer from "./components/Common/Footer";

function App() {
  return (
    <Router>
      <Header/>
      <Routes>
        <Route path="/" element={<Home/>} />
        <Route path="/viduroom/:consultingSeq" element={<ViduRoomWrapper/>} />
        <Route path="/worldcuproom/:consultingSeq" element={<WorldCupRoomWrapper/>} />
        <Route path="login" element={<LogIn/>} />
        <Route path="findid" element={<FindId/>} />
        <Route path="findpw" element={<FindPw/>}/>
        <Route path="authnum" element={<AuthNum/>} />
        <Route path="foundid" element={<FoundId/>} />
        <Route path="changepw" element={<ChangePw/>} />
        <Route path="usertype" element={<UserType/>} />
        <Route path="customersignup" element={<CustomerSignUp/>} />
        <Route path="designersignup" element={<DesignerSignUp/>} />
        <Route path="complete" element={<Complete/>} />
        <Route path="designerdetail/:designerSeq" element={<DesignerDetail/>} />
        <Route path="worlducupimgupload" element={<WorldcupImgUpload/>} />
        <Route path="customermypage/:customerSeq" element={<CustomerMyPage/>} >
          <Route path=":consultingSeq" element={<CustomerMyPage/>} />
        </Route>
        <Route path="editcustomerinfo/:customerSeq" element={<EditCustomerInfo/>} />
        <Route path="designermypage/:designerSeq" element={<DesignerMyPage/>} />
        <Route path="listview" element={<ListView/>} />
        <Route path="mapview" element={<MapView/>} />
        <Route path="map" element={<Map/>} />
        <Route path="event" element={<Event/> } />
        <Route path="editdesignerinfo/:designerSeq" element={<EditDesignerInfo/>} />
        <Route path="consultresultpage/:consultingseq" element={<ConsultResultPage/>} />
        <Route path="reservation/:designerSeq" element={<Reservation/>} />
        <Route path="checkreserve"  element={<CheckReserve/>} />
        <Route path="paysuccess"  element={<PaySuccess/>} />
        <Route path="likedesigner" element={<LikeDesigner/>} />
        <Route path="/modaltest" element={<Survey/>}>
          <Route path="/modaltest/1" element={<Survey/>}/>
        </Route>
      </Routes>
      <Footer/>
    </Router>
  )
};

export default App;

