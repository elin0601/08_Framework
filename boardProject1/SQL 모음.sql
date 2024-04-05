/* 계정 생성 (관리자 계정으로 접속) */
ALTER SESSION SET "_ORACLE_SCRIPT"=TRUE;

CREATE USER SPRING_LJS IDENTIFIED BY SPRING1234;

GRANT CONNECT, RESOURCE TO SPRING_LJS;

ALTER USER SPRING_LJS
DEFAULT TABLESPACE USERS
QUOTA 20M ON USERS;

--> 계정 생성 후 접속 방법(새 데이터베이스 추가)

---------------------------------------------------

/* SPRING 계쩡 접속 */

-- "" : 내부에 작성된 글(모양) 그대로 인식 -> 대/소문자 구분
--> "" 작성 권장

-- CHAR(10) : 고정 길이 문자열 10바이트 (최대 2000 바이트)
-- VARCHAR2(10) : 가변길이 문자열 10바이트 (최대 4000바이트)

-- NVARCHAR2(10) : 가변길이 문자열 10글자 (유니코드, 최대 4000 바이트)
-- CLOB : 가변길이 문자열 (최대 4GB) 

/* MEMBER 테이블 생성 */
CREATE TABLE "MEMBER" (
	"MEMBER_NO" NUMBER CONSTRAINT "MEMBER_PK" PRIMARY KEY,
	"MEMBER_EMAIL" NVARCHAR2(50) NOT NULL,
	"MEMBER_PW" NVARCHAR2(100) NOT NULL,
	"MEMBER_NICKNAME" NVARCHAR2(10) NOT NULL,
	"MEMBER_TEL" CHAR(11) NOT NULL,
	"MEMBER_ADDRESS" NVARCHAR2(150),
	"PROFILE_IMG" VARCHAR2(300),
	"ENROLL_DATE" DATE DEFAULT SYSDATE NOT NULL,
	"MEMBER_DEL_FL" CHAR(1) DEFAULT 'N' 
													CHECK("MEMBER_DEL_FL" IN ('Y', 'N')), -- 탈퇴 여부
	"AUTHORITY" NUMBER DEFAULT 1 
										 CHECK("AUTHORITY" IN (1,2))  --권한
);

-- DROP TABLE "MEMBER";

-- 회원 번호 시퀀스 만들기
CREATE SEQUENCE SEQ_MEMBER_NO NOCACHE;

-- 샘플 회원 데이터 삽입
INSERT INTO "MEMBER" 
VALUES(SEQ_MEMBER_NO.NEXTVAL, 
			'member08@kh.or.kr',
			'$2a$10$JGgzAR/Yz/bUSAjpByGuveXMmTgfFgeHn3kljYRVb56nZ2CSmsqc6',
			'샘플1',
			'01012341234',
			NULL,
			NULL,
			DEFAULT,
			DEFAULT,
			DEFAULT);
		
COMMIT;

SELECT * FROM "MEMBER";

-- 로그인
-- -> BCrypt 암호화 사용중
-- -> DB에서 비밀번호 비교 불가능!!
-- -> 그래서 비밀번호(MEMBER_PW)를 조회

-- > 이메일이 일치 + 탈퇴여부(N일 때)??
SELECT MEMBER_NO , MEMBER_EMAIL , MEMBER_NICKNAME , MEMBER_PW , MEMBER_TEL , MEMBER_ADDRESS , PROFILE_IMG , AUTHORITY , 
			TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일" HH24"시" MI"분" SS"초"') ENROLL_DATE
FROM "MEMBER"
WHERE MEMBER_EMAIL =?
AND MEMBER_DEL_FL ='N';

DELETE FROM "MEMBER" 
WHERE MEMBER_NICKNAME  = '샘플3';

COMMIT;

-- 유효한 이메일 형식인 경우 중복 검사 수행(탈퇴 안한 멤버 중)
SELECT COUNT(*) 
FROM "MEMBER"
WHERE MEMBER_DEL_FL = 'N'
AND MEMBER_EMAIL  = 'member01@kh.or.kr';

-- 닉네임
SELECT COUNT(*)
FROM "MEMBER"
WHERE MEMBER_DEL_FL = 'N'
AND MEMBER_NICKNAME = '샘플1';


----------------------------------------------

/* 이메일, 인증 키 저장 테이블 생성 */
CREATE TABLE "TB_AUTH_KEY" (
	"KEY_NO" NUMBER PRIMARY KEY,
	"EMAIL" NVARCHAR2(50) NOT NULL,
	"AUTH_KEY" CHAR(6) NOT NULL,
	"CREATE_TIME" DATE DEFAULT SYSDATE NOT NULL
	);

COMMENT ON COLUMN "TB_AUTH_KEY"."KEY_NO" IS '인증키 구분 번호(시퀀스)';
COMMENT ON COLUMN "TB_AUTH_KEY"."EMAIL" IS '인증 이메일';
COMMENT ON COLUMN "TB_AUTH_KEY"."AUTH_KEY" IS '인증 번호';
COMMENT ON COLUMN "TB_AUTH_KEY"."CREATE_TIME" IS '인증 번호 생성 시간';

CREATE SEQUENCE SEQ_KEY_NO NOCACHE; -- 인증키 구분 번호 시퀀스

SELECT * FROM "TB_AUTH_KEY";

COMMIT;

SELECT COUNT(*)
FROM TB_AUTH_KEY
WHERE EMAIL = #{가입하려는 이메일 입력 값}
AND AUTH_KEY = #{위 이메일로 보낸 인증 번호};


UPDATE "MEMBER" SET MEMBER_ADDRESS = NULL 
WHERE MEMBER_ADDRESS = '11111^^^^^^';

SELECT * FROM "MEMBER";
COMMIT;

DELETE FROM "MEMBER" 
WHERE MEMBER_EMAIL = 'ijisu8022@gmail.com';

UPDATE "MEMBER" SET MEMBER_DEL_FL ='N';

DELETE  FROM "MEMBER" 
WHERE MEMBER_NO=16;


-----------------------------------

-- 파일 업로드 테스트용 테이블
CREATE TABLE "UPLOAD_FILE"(
	FILE_NO NUMBER PRIMARY KEY,
	FILE_PATH VARCHAR2(500) NOT NULL,
	FILE_ORIGINAL_NAME VARCHAR2(300) NOT NULL,
	FILE_RENAME VARCHAR2(100) NOT NULL,
	FILE_UPLOAD_DATE DATE DEFAULT SYSDATE,
	MEMBER_NO NUMBER REFERENCES "MEMBER"
);

COMMENT ON COLUMN "UPLOAD_FILE".FILE_NO IS '파일 번호(PK)';
COMMENT ON COLUMN "UPLOAD_FILE".FILE_PATH IS '클라이언트 요청 경로';
COMMENT ON COLUMN "UPLOAD_FILE".FILE_ORIGINAL_NAME IS '파일 원본명';
COMMENT ON COLUMN "UPLOAD_FILE".FILE_RENAME IS '변경된 파일명';
COMMENT ON COLUMN "UPLOAD_FILE".MEMBER_NO IS 'MEMBER 테이블의 PK(MEMBER_NO) 참조';

SELECT * FROM "UPLOAD_FILE";

CREATE SEQUENCE SEQ_FILE_NO NOCACHE;













