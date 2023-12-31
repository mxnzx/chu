import axios from "axios";

const BASE_URL = 'https://i9b111.q.ssafy.io/api';
// const BASE_URL = 'http://localhost:9090/api';


// session ID get요청
export const getSessionId = async(consultingSeq)=> {

    try {
        console.log("try접근")
        // const response = await axios.get(`${BASE_URL}/consulting/${consultingSeq}`)
        const response = await axios.get(`${BASE_URL}/consulting/${consultingSeq}`)
        console.log(response.data.result)
        return response.data.result
    } catch(error) {
        throw new Error('가져오기 실패')
    }
};