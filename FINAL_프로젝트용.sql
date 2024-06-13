SELECT * FROM RESERVATION;

ALTER TABLE RESERVATION 
RENAME COLUMN RESERV_NUMBER TO RESERV_COUNT;

-- ALTER TABLE 테이블명 ADD(컬럼명 데이터타입 [DEFAULT '값']);
ALTER TABLE RESERVATION ADD (RESERV_REQUEST NVARCHAR2(1000));

SELECT * FROM "STORE";

INSERT INTO "STORE" VALUES(
	SEQ_STORE_NO.NEXTVAL,
	'기와탭룸',
	'삼청동 한옥카페말고 한옥펍에서 즐기는 수제맥주',
	'서울 종로구 율곡로1길 74-7 1층',
	DEFAULT,
	10,
	'02-733-1825',
	'12:00',
	'23:00',
	NULL,
	NULL,		
	DEFAULT,
	'/images/profile/20240503154154_00016.jpg',
	4.42,
	2,
	5
);	

COMMIT;

ROLLBACK;

SELECT * FROM "MEMBER";

SELECT STORE_NO, STORE_NAME, STORE_INFO, STORE_LOCATION, STORE_STATUS, STORE_MAX_NUMBER, STORE_TEL, OPEN_HOUR, CLOSE_HOUR,
				BREAKTIME_START, BREAKTIME_END, STORE_CLOSED, STORE_IMG, TOTAL_RATING, MEMBER_NO, STORE_MAX_TABLE
				
FROM "STORE"
--JOIN "WEEK_OFF" USING (STORE_NO)
WHERE STORE_NO=1
AND STORE_CLOSED = 'N'
--OR OFF_WEEK_NO IS NULL;

SELECT STORE_NAME  ,STORE_MAX_TABLE 
FROM "STORE";

SELECT * FROM "STORE";
SELECT * FROM "WEEK_OFF";

SELECT * FROM "RESERVATION";

--컬럼 추가 : ALTER TABLE 테이블명 ADD(컬럼명 데이터타입 [DEFAULT '값']);

ALTER TABLE "RESERVATION" ADD (VISIT_NAME NVARCHAR2(15));
ALTER TABLE "RESERVATION" ADD (VISIT_TEL CHAR(11));

------------------------------------------------------

SELECT *
FROM "STORE";

SELECT STORE_NO 
FROM "STORE"
WHERE STORE_NAME ='테스트';


SELECT OPEN_HOUR , CLOSE_HOUR, BREAKTIME_START , BREAKTIME_END
FROM "STORE"
WHERE STORE_NO =1
AND STORE_CLOSED = 'N';

 SELECT S.STORE_NAME, S.STORE_INFO, S.STORE_LOCATION, S.STORE_STATUS,
       S.STORE_TEL, S.OPEN_HOUR, S.CLOSE_HOUR, S.BREAKTIME_START, S.BREAKTIME_END,
       S.STORE_CLOSED, S.STORE_IMG, S.TOTAL_RATING, S.MEMBER_NO, S.STORE_NO,
       M.MENU_NO, M.MENU_TITLE, M.MENU_AMOUNT 

		FROM "STORE" S
		LEFT JOIN STORE_CATEGORY SC ON S.STORE_NO = SC.STORE_NO
		LEFT JOIN MENU M ON S.STORE_NO = M.STORE_NO
		WHERE S.STORE_NO = 1;
	 


INSERT INTO "RESERVATION" VALUES(
	SEQ_RESERV_NO.NEXTVAL,
	RESERV_DATE,
	RESERV_TIME,
	RESERV_COUNT, 
	DEFAULT,
	RESERV_REQUEST,
	MEMBER_NO,
	STORE_NO,
	VISIT_NAME,
	VISIT_TEL
	
)

SELECT *
FROM "RESERVATION";

SELECT OPEN_HOUR , CLOSE_HOUR, BREAKTIME_START , BREAKTIME_END
		FROM "STORE"
		WHERE STORE_NO = 1
		AND STORE_CLOSED = 'N';
		
	
	
SELECT COUNT(*)
	FROM "RESERVATION"
	JOIN "STORE" USING (STORE_NO)
	WHERE RESERV_DATE = TO_CHAR(RESERV_DATE, 'YYYY-MM-DD')
	AND RESERV_TIME = '17:00'
	AND RESERV_STATUS_FL IN ('Y', 'N');
	
-- 해당 시간에 예약 건수

SELECT COUNT(*) 
FROM "RESERVATION"
JOIN "STORE" USING (STORE_NO)
WHERE RESERV_DATE  IN (
	SELECT TO_CHAR(RESERV_DATE, 'MM.DD') "RESERV_DATE" 
	FROM "RESERVATION"
	JOIN "STORE" USING (STORE_NO)
	WHERE RESERV_DATE = '2024-05-06'
) 
AND STORE_NO = '1'
AND STORE_CLOSED = 'N'	
AND RESERV_STATUS_FL IN ('Y', 'N');


SELECT TO_CHAR(RESERV_DATE, 'YYYY-MM-DD')
FROM "RESERVATION"
WHERE RESERV_DATE = '2024-05-06'
AND STORE_NO = '1';

SELECT RESERV_TIME , COUNT(RESERV_TIME) "COUNTS"
FROM "RESERVATION"
JOIN "STORE" USING (STORE_NO)
WHERE TO_CHAR(RESERV_DATE, 'MM.DD') IN (
    SELECT TO_CHAR(TO_DATE(RESERV_DATE, 'YYYY-MM-DD'), 'MM.DD') "RESERV_DATE"
    FROM "RESERVATION"
    WHERE TO_CHAR(RESERV_DATE, 'YYYY-MM-DD') = '2024-0'
)
AND STORE_NO = '1'
AND STORE_CLOSED = 'N'
AND RESERV_STATUS_FL IN ('Y', 'N')
GROUP BY RESERV_TIME;


COMMIT;

ROLLBACK;


-- 시간볗 최대 예약 가능함 테이블 수
SELECT STORE_MAX_TABLE 
FROM "STORE"
WHERE STORE_NO = 1;
	
SELECT *
FROM "RESERVATION";

UPDATE "RESERVATION" SET RESERV_STATUS_FL = 'Y'
WHERE RESERV_NO = 70 ;

DELETE FROM "RESERVATION"
WHERE RESERV_NO = 129;

SELECT *
FROM "STORE";

SELECT *
FROM "OFF_WEEK";

SELECT *
FROM "OFF_DAY";

-- 고정 휴일, 지정 휴일 조회
SELECT OFF_DAY_NO, OFF_DAY_TITLE , TO_CHAR(OFF_DAY_START, 'YYYY-MM-DD') OFF_DAY_START, 
			   TO_CHAR(OFF_DAY_END, 'YYYY-MM-DD') OFF_DAY_END
FROM "OFF_DAY"
WHERE STORE_NO ='0104534247';

SELECT STORE_NO, MEMBER_NO
FROM "MEMBER"
JOIN "STORE" USING (MEMBER_NO)
WHERE MEMBER_CODE = 2;


SELECT M.MEMBER_NO, TO_CHAR(R.RESERV_DATE, 'YYYY-MM-DD') "RESERV_DATE", S.STORE_NAME, M.MEMBER_NICKNAME
FROM "MEMBER" M
JOIN "RESERVATION" R ON M.MEMBER_NO = R.MEMBER_NO
JOIN "STORE" S ON R.STORE_NO = S.STORE_NO
WHERE R.RESERV_NO = '397';

SELECT MEMBER_NICKNAME, MEMBER_NO
FROM "MEMBER";


SELECT * 
FROM "RESERVATION";

ALTER TABLE "NOTIFICATION" (NOTIFICATION_TYPE NVARCHAR2(1000));

SELECT COUNT(*) 
FROM "NOTIFICATION"
JOIN "STORE" ON (RECEIVE_MEMBER_NO  = MEMBER_NO)
WHERE MEMBER_NO = 1;

SELECT MEMBER_NO, MEMBER_NICKNAME, MEMBER_NAME,MEMBER_ID , STORE_NO, STORE_NAME, STORE_MAX_TABLE
FROM "MEMBER"
JOIN "STORE" USING (MEMBER_NO);

DELETE FROM "RESERVATION"
WHERE STORE_NO =  '1000112345';

COMMIT;

SELECT *
FROM "RESERVATION"
JOIN "STORE" USING (STORE_NO);

SELECT * 
FROM "NOTIFICATION";

SELECT *
FROM "STORE";

SELECT S.MEMBER_NO, TO_CHAR(R.RESERV_DATE, 'YYYY-MM-DD') "RESERV_DATE", S.STORE_NAME, M.MEMBER_NICKNAME
		FROM "MEMBER" M
		JOIN "RESERVATION" R ON M.MEMBER_NO = R.MEMBER_NO
		JOIN "STORE" S ON R.STORE_NO = S.STORE_NO
		
SELECT *
FROM "STORE";

UPDATE "STORE" SET STORE_MAX_TABLE  = 10
WHERE STORE_NAME = '기와탭룸';

SELECT *
FROM "OFF_WEEK";



ALTER TABLE "NOTIFICATION" ADD NOTI_CODE NUMBER;


COMMENT ON COLUMN "NOTIFICATION"."NOTI_CODE" IS '알림카테고리코드';

CREATE TABLE "NOTI_CATEGORY" (
	"NOTI_CODE"	NUMBER		NOT NULL,
	"NOTI_TYPE"	NVARCHAR2(20)		NOT NULL
);

COMMENT ON COLUMN "NOTI_CATEGORY"."NOTI_CODE" IS '알림카테고리코드';

COMMENT ON COLUMN "NOTI_CATEGORY"."NOTI_TYPE" IS '알림종류(전체, 신고 ....)';


ALTER TABLE "NOTI_CATEGORY" ADD CONSTRAINT "PK_NOTI_CATEGORY" PRIMARY KEY (
	"NOTI_CODE"
);

ALTER TABLE "NOTIFICATION" ADD CONSTRAINT "FK_MEMBER_TO_NOTIFICATION_1" FOREIGN KEY (
	"RECEIVE_MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "NOTIFICATION" ADD CONSTRAINT "FK_NOTI_CATEGORY_TO_NOTIFICATION_1" FOREIGN KEY (
	"NOTI_CODE"
)
REFERENCES "NOTI_CATEGORY" (
	"NOTI_CODE"
);

SELECT *
FROM "NOTI_CATEGORY";

INSERT INTO "NOTI_CATEGORY" VALUES(1, '승인');
INSERT INTO "NOTI_CATEGORY" VALUES(2, '취소');
INSERT INTO "NOTI_CATEGORY" VALUES(3, '리뷰신고');
INSERT INTO "NOTI_CATEGORY" VALUES(4, '거절');
INSERT INTO "NOTI_CATEGORY" VALUES(5, '리뷰');
INSERT INTO "NOTI_CATEGORY" VALUES(6, '가게신고');
INSERT INTO "NOTI_CATEGORY" VALUES(0, '전체');


DROP TABLE "NOTI_CATEGORY";

SELECT *
FROM "NOTIFICATION";

UPDATE "NOTI_CATEGORY" SET  NOTI_TYPE  = '승인'
WHERE NOTI_CODE = 1;

UPDATE "NOTI_CATEGORY" SET  NOTI_TYPE = '리뷰신고'
WHERE NOTI_CODE = 3;

SELECT * FROM "NOTI_CATEGORY";

COMMIT;

SELECT *
FROM "RESERVATION";

SELECT S.MEMBER_NO, TO_CHAR(R.RESERV_DATE, 'YYYY-MM-DD') "RESERV_DATE", S.STORE_NO , S.STORE_NAME, M.MEMBER_NICKNAME
FROM "MEMBER" M
JOIN "RESERVATION" R ON M.MEMBER_NO = R.MEMBER_NO
JOIN "STORE" S ON R.STORE_NO = S.STORE_NO
WHERE R.RESERV_NO = 440;

SELECT *
FROM "STORE"
WHERE STORE_NO = '1';

UPDATE "STORE" SET STORE_IMG = '/images/store/20240531144038_00001.jpg'
WHERE STORE_NO = '1';

ROLLBACK;

DELETE FROM "NOTI_CATEGORY"
WHERE NOTI_CODE = 0;

SELECT TO_CHAR(RESERV_DATE, 'YYYY-MM-DD')
FROM "RESERVATION";

SELECT MEMBER_NO 
FROM "RESERVATION"
WHERE TO_CHAR(RESERV_DATE, 'YYYY-MM-DD') = '2024-06-04'
AND RESERV_NO = 443;

SELECT *
FROM "RESERVATION"
WHERE RESERV_NO = 443;

SELECT RESERV_TIME , COUNT(RESERV_TIME) "COUNTS"
		FROM "RESERVATION"
		JOIN "STORE" USING (STORE_NO)
		WHERE TO_CHAR(RESERV_DATE, 'MM.DD') IN (
		    SELECT TO_CHAR(TO_DATE(RESERV_DATE, 'YYYY-MM-DD'), 'MM.DD') "RESERV_DATE"
		    FROM "RESERVATION"
		    WHERE TO_CHAR(RESERV_DATE, 'YYYY-MM-DD') = '2024-06-28'
		)
		AND STORE_NO = '1'
		AND STORE_CLOSED = 'N'
		AND RESERV_STATUS_FL IN ('Y', 'N')
		GROUP BY RESERV_DATE, RESERV_TIME;

SELECT M.MEMBER_NO, M.MEMBER_NICKNAME , TO_CHAR(R.RESERV_DATE, 'YYYY-MM-DD') "RESERV_DATE", R.STORE_NO
FROM "MEMBER" M
JOIN "RESERVATION" R ON (M.MEMBER_NO = R.MEMBER_NO)
WHERE R.RESERV_NO = 443;

SELECT V.REVIEW_NO , M.MEMBER_NICKNAME, V.STORE_NO , M.MEMBER_NO, V.REVIEW_NO 
FROM "REVIEW" V
JOIN "MEMBER" M ON (M.MEMBER_NO = V.MEMBER_NO)
WHERE V.REVIEW_NO = 174;

SELECT MEMBER_NO 
FROM "REVIEW"
WHERE REVIEW_NO = 174;

SELECT *
FROM "MEMBER";

SELECT *
FROM "REVIEW_REPLY";

UPDATE "RESERVATION" SET RESERV_STATUS_FL = 'N'
WHERE RESERV_STATUS_FL = 'C';

SELECT MEMBER_NO
FROM "MEMBER"
WHERE MEMBER_CODE = 3
AND MEMBER_NO = 3;

ROLLBACK;

COMMIT;

SELECT MEMBER_NO 
FROM "REVIEW"
WHERE REVIEW_NO = 135;

SELECT * FROM "RESERVATION";


SELECT M.MEMBER_NICKNAME , TO_CHAR(R.RESERV_DATE, 'YYYY-MM-DD') "RESERV_DATE", R.RESERV_NO , R.STORE_NO 
FROM "RESERVATION" R 
JOIN "MEMBER" M  ON (R.MEMBER_NO = M.MEMBER_NO)
WHERE RESERV_NO = 543;

SELECT STORE_NAME,
FROM "STORE"
WHERE STORE_NO = '1';

SELECT MEMBER_NICKNAME , TO_CHAR(RESERV_DATE, 'YYYY-MM-DD') "RESERV_DATE", MEMBER_NO 
FROM "RESERVATION"
JOIN "MEMBER" USING (MEMBER_NO)
WHERE RESERV_NO = 443;
 

SELECT *
FROM "RESERVATION";

SELECT MEMBER_NO  
FROM  "STORE"
WHERE STORE_NO  = '1234567890';


SELECT MEMBER_NO
FROM "MEMBER";

SELECT STORE_NAME, REVIEW_NO 
FROM "REVIEW"
JOIN "STORE" USING (STORE_NO)
WHERE REVIEW_NO = 160;


SELECT *
FROM "NOTIFICATION";

--REPORT_NO , TO_CHAR(REPORT_DATE, 'YYYY-MM-DD') "REPORT_DATE"
--
SELECT R.REPORT_NO , TO_CHAR(R.REPORT_DATE, 'YYYY-MM-DD') "REPORT_DATE", S.STORE_NAME
FROM "STORE" S 
JOIN "REVIEW" V ON(V.STORE_NO = S.STORE_NO)
JOIN "REPORT" R ON (R.MEMBER_NO = V.MEMBER_NO)
WHERE REPORT_NO = 53;


SELECT STORE_NAME
FROM "REPORT"
JOIN "REVIEW" USING (MEMBER_NO)
JOIN "STORE" USING (STORE_NO);

-- 알림 받는 사람 : 댓글을 작성한 회원, 가게 사장
SELECT *
FROM "REPORT" R
JOIN "MEMBER" M ON (R.MEMBER_NO = M.MEMBER_NO)
JOIN "REVIEW" V ON (M.MEMBER_NO = V.MEMBER_NO)
JOIN "STORE" S ON (V.STORE_NO = S.STORE_NO)
WHERE REPORT_NO = 53 ;

SELECT *
FROM "MEMBER";

SELECT R.REPORT_NO, TO_CHAR(R.REPORT_DATE, 'YYYY-MM-DD') "REPORT_DATE", S.STORE_NAME, V.MEMBER_NO , S.STORE_NO 
FROM "REVIEW" V
JOIN "REPORT" R ON (V.REVIEW_NO = R.REVIEW_NO)
JOIN "STORE" S ON (S.STORE_NO = V.STORE_NO)
WHERE REPORT_NO = 184;

UPDATE "REPORT" SET REPORT_DEL_FL ='N'
WHERE REPORT_DEL_FL ='Y';

COMMIT;

SELECT S.STORE_NO, R.MEMBER_NO , S.STORE_NAME, R.REQUEST_NO
FROM "REQUEST_EDIT" R
JOIN "STORE" S ON (R.STORE_NO = S.STORE_NO)
WHERE REQUEST_NO = 14;



SELECT M.MEMBER_NICKNAME , TO_CHAR(R.RESERV_DATE, 'MM.DD(DY)') RESERV_DATE, R.RESERV_NO, R.STORE_NO , R.RESERV_TIME
		FROM "RESERVATION" R 
		JOIN "MEMBER" M  ON (R.MEMBER_NO = M.MEMBER_NO);

SELECT *
FROM "REPORT";

SELECT R.REPORT_NO, TO_CHAR(R.REPORT_DATE, 'YYYY-MM-DD') "REPORT_DATE", S.STORE_NAME, V.MEMBER_NO , S.STORE_NO 
		FROM "REVIEW" V
		JOIN "REPORT" R ON (V.REVIEW_NO = R.REVIEW_NO)
		JOIN "STORE" S ON (S.STORE_NO = V.STORE_NO);
	
SELECT MEMBER_NO 
		FROM "REVIEW"
		WHERE REVIEW_NO = 134;
	
	SELECT V.MEMBER_NO , V.REVIEW_NO , S.STORE_NAME , R.REPORT_CONTENT 
		FROM "REVIEW" V
		JOIN "REPORT" R ON (V.REVIEW_NO = R.REVIEW_NO)
		JOIN "STORE" S ON (S.STORE_NO = V.STORE_NO)
		WHERE REPORT_NO = 134;
	
	-- 224
	SELECT V.REVIEW_NO , M.MEMBER_NICKNAME, V.STORE_NO , M.MEMBER_NO
		FROM "REVIEW" V
		JOIN "MEMBER" M ON (M.MEMBER_NO = V.MEMBER_NO);
		
	SELECT MEMBER_NO 
	FROM "REVIEW"
	WHERE REVIEW_NO = 134;
	
SELECT M.MEMBER_FLAG, 
FROM "RESERVATION" R
JOIN "MEMBER" M ON (R.MEMBER_NO = M.MEMBER_NO)
WHERE R.RESERV_STATUS_FL = 'X';

SELECT MEMBER_NICKNAME 
FROM "MEMBER"
WHERE MEMBER_NO = 1;

SELECT MEMBER_FLAG 
FROM "MEMBER"
WHERE MEMBER_NO = 1;

UPDATE "MEMBER" SET MEMBER_FLAG = 0
WHERE MEMBER_NO = 2;
COMMIT;

SELECT MEMBER_NO
FROM "MEMBER"
JOIN "STORE" USING (MEMBER_NO)
WHERE STORE_NO = '1000112345';
WHERE 

SELECT *
FROM "REVIEW";

SELECT *
FROM "NOTIFICATION"
WHERE NOTI_CODE = 5;

SELECT *
FROM "NOTI_CATEGORY";

INSERT INTO "NOTI_CATEGORY" VALUES(2, 승인);

UPDATE "NOTI_CATEGORY" SET NOTI_CODE = 3
WHERE NOTI_CODE = 5;

DELETE FROM "NOTI_CATEGORY";

INSERT INTO "NOTI_CATEGORY" VALUES(0, '전체');
INSERT INTO "NOTI_CATEGORY" VALUES(1, '예약 승인');
INSERT INTO "NOTI_CATEGORY" VALUES(2, '예약 거절');
INSERT INTO "NOTI_CATEGORY" VALUES(3, '리뷰');
INSERT INTO "NOTI_CATEGORY" VALUES(4, '리뷰 신고');
INSERT INTO "NOTI_CATEGORY" VALUES(5, '가게 신고');


ROLLBACK;
