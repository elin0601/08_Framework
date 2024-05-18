SELECT * FROM RESERVATION;

ALTER TABLE RESERVATION 
RENAME COLUMN RESERV_NUMBER TO RESERV_COUNT;

-- ALTER TABLE 테이블명 ADD(컬럼명 데이터타입 [DEFAULT '값']);
ALTER TABLE RESERVATION ADD (RESERV_REQUEST NVARCHAR2(1000));

SELECT * FROM STORE;

INSERT INTO "STORE" VALUES(
	SEQ_STORE_NO.NEXTVAL,
	'기와탭룸',
	'삼청동 한옥카페말고 한옥펍에서 즐기는 수제맥주',
	'서울 종로구 율곡로1길 74-7 1층',
	DEFAULT,
	'02-733-1825',
	'12:00',
	'23:00',
	NULL,
	DEFAULT,
	'/images/profile/20240503154154_00016.jpg',
	3,
	4.42
);	

COMMIT;

SELECT * FROM MEMBER;