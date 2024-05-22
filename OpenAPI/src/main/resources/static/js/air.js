const serviceKey = "lIH66KzXaSQkB/Wf3abVrY7DtIes9ltY0OHT0X/UwGgxf4lcNgWvxpwieVKCx+O/LBsDn1UlSlwvyN5clkML4A==";

const numOfRows = 1000; // 조회할 데이터 개수
const pageNo = 1; // 조회할 페이지
const dataType = "JSON"; // 응답 데이터 타입 (JSON/XML)
const ver = 1.0;

const getAirPollution = (sidoName) => {


const requestUrl = 'http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty';

// 쿼리 스트링 생성 (URLSearchParams.toString())

const searchParams = new URLSearchParams();

searchParams.append("serviceKey", serviceKey);
searchParams.append("returnType", dataType);
searchParams.append("numOfRows", numOfRows);
searchParams.append("pageNo", pageNo);
searchParams.append("sidoName", sidoName);
searchParams.append("ver", ver);


fetch(`${requestUrl}?${searchParams.toString()}`)
.then(resp => resp.json())
.then(result => {

    console.log(result);

})

 .catch(e => console.log(e));

}
