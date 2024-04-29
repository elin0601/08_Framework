CREATE TABLE "MEMBER" (
	"MEMBER_NO"	NUMBER		NOT NULL,
	"MEMBER_EMAIL"	NVARCHAR2(50)		NOT NULL,
	"MEMBER_PW"	NVARCHAR2(100)		NOT NULL,
	"MEMBER_NICKNAME"	NVARCHAR2(10)		NOT NULL,
	"MEMBER_TEL"	CHAR(11)		NOT NULL,
	"MEMBER_ADDRESS"	NVARCHAR2(300)		NULL,
	"PROFILE_IMG"	VARCHAR2(300)		NULL,
	"ENROLL_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"MEMBER_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"AUTHORITY"	CHAR(1)	DEFAULT 'N'	NOT NULL
);

COMMENT ON COLUMN "MEMBER"."MEMBER_NO" IS '회원번호(PK)';

COMMENT ON COLUMN "MEMBER"."MEMBER_EMAIL" IS '회원 이메일';

COMMENT ON COLUMN "MEMBER"."MEMBER_PW" IS '회원 비밀번호';

COMMENT ON COLUMN "MEMBER"."MEMBER_NICKNAME" IS '회원 닉네임';

COMMENT ON COLUMN "MEMBER"."MEMBER_TEL" IS '회원 전화번호';

COMMENT ON COLUMN "MEMBER"."MEMBER_ADDRESS" IS '회원 주소';

COMMENT ON COLUMN "MEMBER"."PROFILE_IMG" IS '프로필 이미지';

COMMENT ON COLUMN "MEMBER"."ENROLL_DATE" IS '회원 가입일';

COMMENT ON COLUMN "MEMBER"."MEMBER_DEL_FL" IS '탈퇴여부(Y, N)';

COMMENT ON COLUMN "MEMBER"."AUTHORITY" IS '회원 권한(관리자 Y)';

CREATE TABLE "BOARD" (
	"BOARD_NO"	NUMBER		NOT NULL,
	"BOARD_TITLE"	NVARCHAR2(100)		NOT NULL,
	"BOARD_CONTENT"	VARCHAR2(4000)		NOT NULL,
	"BOARD_WRITE_DATE"	DATE		NOT NULL,
	"BOARD_UPDATE_DATE"	DATE		NULL,
	"READ_COUNT"	NUMBER	DEFAULT 0	NOT NULL,
	"BOARD_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"BOARD_CODE"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "BOARD"."BOARD_NO" IS '게시글 번호(PK)';

COMMENT ON COLUMN "BOARD"."BOARD_TITLE" IS '게시글 제목';

COMMENT ON COLUMN "BOARD"."BOARD_CONTENT" IS '게시글 내용';

COMMENT ON COLUMN "BOARD"."BOARD_WRITE_DATE" IS '게시글 작성일';

COMMENT ON COLUMN "BOARD"."BOARD_UPDATE_DATE" IS '게시글 마지막 수정일';

COMMENT ON COLUMN "BOARD"."READ_COUNT" IS '조회수';

COMMENT ON COLUMN "BOARD"."BOARD_DEL_FL" IS '게시글 삭제여부( Y, N)';

COMMENT ON COLUMN "BOARD"."MEMBER_NO" IS '회원번호(PK)';

COMMENT ON COLUMN "BOARD"."BOARD_CODE" IS '게시판 코드 번호(PK)';

CREATE TABLE "BOARD_LIKE" (
	"MEMBER_NO"	NUMBER		NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "BOARD_LIKE"."MEMBER_NO" IS '회원번호(PK)';

COMMENT ON COLUMN "BOARD_LIKE"."BOARD_NO" IS '게시글 번호(PK)';

CREATE TABLE "BOARD_IMG" (
	"IMG_NO"	NUMBER		NOT NULL,
	"IMG_PATH"	VARCHAR2(200)		NOT NULL,
	"IMG_ORIGINAL_NAME"	NVARCHAR2(50)		NOT NULL,
	"IMG_RENAME"	NVARCHAR2(50)		NOT NULL,
	"IMG_ORDER"	NUMBER		NULL,
	"BOARD_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "BOARD_IMG"."IMG_NO" IS '이미지번호(PK)';

COMMENT ON COLUMN "BOARD_IMG"."IMG_PATH" IS '이미지 요청 경로';

COMMENT ON COLUMN "BOARD_IMG"."IMG_ORIGINAL_NAME" IS '이미지 원본명';

COMMENT ON COLUMN "BOARD_IMG"."IMG_RENAME" IS '이미지 변경명';

COMMENT ON COLUMN "BOARD_IMG"."IMG_ORDER" IS '이미지 순서';

COMMENT ON COLUMN "BOARD_IMG"."BOARD_NO" IS '게시글 번호(PK)';

CREATE TABLE "COMMENT" (
	"COMMENT_NO"	NUMBER		NOT NULL,
	"COMMENT_CONTENT"	VARCHAR2(4000)		NOT NULL,
	"COMMENT_WRITE_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"COMMENT_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL,
	"PARENT_COMMENT_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "COMMENT"."COMMENT_NO" IS '댓글번호(PK)';

COMMENT ON COLUMN "COMMENT"."COMMENT_CONTENT" IS '댓글 내용';

COMMENT ON COLUMN "COMMENT"."COMMENT_WRITE_DATE" IS '댓글 작성일';

COMMENT ON COLUMN "COMMENT"."COMMENT_DEL_FL" IS '댓글 삭제여부 (Y, N)';

COMMENT ON COLUMN "COMMENT"."MEMBER_NO" IS '회원번호(PK)';

COMMENT ON COLUMN "COMMENT"."BOARD_NO" IS '게시글 번호(PK)';

COMMENT ON COLUMN "COMMENT"."PARENT_COMMENT_NO" IS '댓글번호(PK)';

CREATE TABLE "BOARD_TYPE" (
	"BOARD_CODE"	NUMBER		NOT NULL,
	"BOARD_NAME"	NVARCHAR2(20)		NOT NULL
);

COMMENT ON COLUMN "BOARD_TYPE"."BOARD_CODE" IS '게시판 코드 번호(PK)';

COMMENT ON COLUMN "BOARD_TYPE"."BOARD_NAME" IS '게시판 명';

CREATE TABLE "CATEGORY" (
	"CATEGORY_NO"	NUMBER		NOT NULL,
	"BOARD_CODE"	NUMBER		NOT NULL,
	"CATEGORY_NAME"	VARCHAR2(20)		NULL
);

COMMENT ON COLUMN "CATEGORY"."CATEGORY_NO" IS '게시판 카테고리(PK)';

COMMENT ON COLUMN "CATEGORY"."BOARD_CODE" IS '게시판 코드 번호(PK)';

COMMENT ON COLUMN "CATEGORY"."CATEGORY_NAME" IS '게시판 카테고리 이름';

ALTER TABLE "MEMBER" ADD CONSTRAINT "PK_MEMBER" PRIMARY KEY (
	"MEMBER_NO"
);

ALTER TABLE "BOARD" ADD CONSTRAINT "PK_BOARD" PRIMARY KEY (
	"BOARD_NO"
);

ALTER TABLE "BOARD_LIKE" ADD CONSTRAINT "PK_BOARD_LIKE" PRIMARY KEY (
	"MEMBER_NO",
	"BOARD_NO"
);

ALTER TABLE "BOARD_IMG" ADD CONSTRAINT "PK_BOARD_IMG" PRIMARY KEY (
	"IMG_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "PK_COMMENT" PRIMARY KEY (
	"COMMENT_NO"
);

ALTER TABLE "BOARD_TYPE" ADD CONSTRAINT "PK_BOARD_TYPE" PRIMARY KEY (
	"BOARD_CODE"
);

ALTER TABLE "CATEGORY" ADD CONSTRAINT "PK_CATEGORY" PRIMARY KEY (
	"CATEGORY_NO"
);

ALTER TABLE "BOARD" ADD CONSTRAINT "FK_MEMBER_TO_BOARD_1" FOREIGN KEY (
    "MEMBER_NO"
)
REFERENCES "MEMBER" (
    "MEMBER_NO"
);

ALTER TABLE "BOARD" ADD CONSTRAINT "FK_BOARD_TYPE_TO_BOARD_1" FOREIGN KEY (
	"BOARD_CODE"
)
REFERENCES "BOARD_TYPE" (
	"BOARD_CODE"
);

ALTER TABLE "BOARD_LIKE" ADD CONSTRAINT "FK_MEMBER_TO_BOARD_LIKE_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "BOARD_LIKE" ADD CONSTRAINT "FK_BOARD_TO_BOARD_LIKE_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

ALTER TABLE "BOARD_IMG" ADD CONSTRAINT "FK_BOARD_TO_BOARD_IMG_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_MEMBER_TO_COMMENT_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_BOARD_TO_COMMENT_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_COMMENT_TO_COMMENT_1" FOREIGN KEY (
	"PARENT_COMMENT_NO"
)
REFERENCES "COMMENT" (
	"COMMENT_NO"
);

ALTER TABLE "CATEGORY" ADD CONSTRAINT "FK_BOARD_TYPE_TO_CATEGORY_1" FOREIGN KEY (
	"BOARD_CODE"
)
REFERENCES "BOARD_TYPE" (
	"BOARD_CODE"
);

COMMIT;

ALTER TABLE "BOARD" ADD (CATEGORY_NO NUMBER);

ALTER TABLE "BOARD" 
ADD CONSTRAINT FK_CATEGORY_TO_BOARD_1 
FOREIGN KEY (CATEGORY_NO) REFERENCES "CATEGORY";


/* 이메일, 인증 키 저장 테이블 생성 */
CREATE TABLE "AUTH_KEY" (
	"KEY_NO" NUMBER PRIMARY KEY,
	"EMAIL" NVARCHAR2(50) NOT NULL,
	"AUTH_KEY" CHAR(6) NOT NULL,
	"CREATE_TIME" DATE DEFAULT SYSDATE NOT NULL
);

COMMENT ON COLUMN "AUTH_KEY"."KEY_NO" IS '인증키 구분 번호(시퀀스)';
COMMENT ON COLUMN "AUTH_KEY"."EMAIL" IS '인증 이메일';
COMMENT ON COLUMN "AUTH_KEY"."AUTH_KEY" IS '인증 번호';
COMMENT ON COLUMN "AUTH_KEY"."CREATE_TIME" IS '인증 번호 생성 시간';

CREATE SEQUENCE SEQ_KEY_NO NOCACHE; 


-- 샘플 데이터
CREATE SEQUENCE SEQ_MEMBER_NO NOCACHE;

INSERT INTO "MEMBER" VALUES (
	SEQ_MEMBER_NO.NEXTVAL , 'ijisu8022@gmail.com', '$2a$10$vmMu5ZJ/qMaavveCP7TzFOLcQLn9ETUR5dNJrb23xPFUI2tm8Xemm', '샘플1', '01012341234', '서울시', NULL, SYSDATE, DEFAULT, DEFAULT
);

INSERT INTO "MEMBER" VALUES (
	SEQ_MEMBER_NO.NEXTVAL , 'qwer@gmail.com', '$2a$10$vmMu5ZJ/qMaavveCP7TzFOLcQLn9ETUR5dNJrb23xPFUI2tm8Xemm', '샘플2', '01012341234', '서울시', NULL, SYSDATE, DEFAULT, DEFAULT
);

SELECT * FROM "MEMBER";

/* 이메일, 인증 키 저장 테이블 생성 */
CREATE TABLE "AUTH_KEY" (
	"KEY_NO" NUMBER PRIMARY KEY,
	"EMAIL" NVARCHAR2(50) NOT NULL,
	"AUTH_KEY" CHAR(6) NOT NULL,
	"CREATE_TIME" DATE DEFAULT SYSDATE NOT NULL
);

COMMENT ON COLUMN "AUTH_KEY"."KEY_NO" IS '인증키 구분 번호(시퀀스)';
COMMENT ON COLUMN "AUTH_KEY"."EMAIL" IS '인증 이메일';
COMMENT ON COLUMN "AUTH_KEY"."AUTH_KEY" IS '인증 번호';
COMMENT ON COLUMN "AUTH_KEY"."CREATE_TIME" IS '인증 번호 생성 시간';

CREATE SEQUENCE SEQ_KEY_NO NOCACHE; -- 인증키 구분 번호 시퀀스

SELECT * FROM AUTH_KEY;
--------------------------------------------------------------------------------------

-- 회원가입 
INSERT INTO "MEMBER" 
VALUES (
	SEQ_MEMBER_NO.NEXTVAL, 'test01@naver.com', '$2a$10$vmMu5ZJ/qMaavveCP7TzFOLcQLn9ETUR5dNJrb23xPFUI2tm8Xemm', '회원가입샘플1', '01013441233', '서울시 중구 남대문로 120', NULL, SYSDATE, DEFAULT, DEFAULT);

-- 이메일 중복 검사
SELECT COUNT(*)
FROM "MEMBER"
WHERE MEMBER_DEL_FL='N'
AND AUTHORITY = 'N'
AND MEMBER_EMAIL = 'test01@naver.com';

-- 닉네임 중복 검사
SELECT COUNT(*)
FROM "MEMBER"
WHERE MEMBER_DEL_FL='N'
AND AUTHORITY = 'N'
AND MEMBER_NICKNAME = '회원가입샘플1';


-- ID 찾기
SELECT MEMBER_EMAIL 
FROM "MEMBER"
WHERE MEMBER_NICKNAME ='샘플'
AND MEMBER_TEL = '01012341234'
AND MEMBER_DEL_FL = 'N'
AND AUTHORITY = 'N';

SELECT * FROM "MEMBER";

-- PW 찾기
SELECT MEMBER_PW 
FROM "MEMBER"
WHERE MEMBER_NICKNAME ='샘플1'
AND MEMBER_TEL = '01012341234'
AND MEMBER_EMAIL = 'ijisu8022@gmail.com'
AND MEMBER_DEL_FL = 'N'
AND AUTHORITY = 'N';


/* 인증번호 발송 후 데이터 삭제 확인 */
SELECT COUNT(*)
FROM "AUTH_KEY"
WHERE EMAIL = '03dreams@naver.com'
AND AUTH_KEY = '334751'
AND 24 / 60 * 5 >= (SYSDATE - CREATE_TIME) / 24 / 60;

SELECT COUNT(*)
FROM "MEMBER"
WHERE MEMBER_DEL_FL  = 'N'
AND AUTHORITY = 'N'
AND MEMBER_NICKNAME = '애플이'
AND MEMBER_EMAIL = 'sotoddlf03@icloud.com'
AND MEMBER_TEL = '01033334444';

UPDATE "MEMBER" SET MEMBER_PW = '$2a$10$vmMu5ZJ/qMaavveCP7TzFOLcQLn9ETUR5dNJrb23xPFUI2tm8Xemm'
WHERE MEMBER_NO = 2;

-------
ALTER TABLE "COMMENT" 
MODIFY PARENT_COMMENT_NO NUMBER NULL;


/* 댓글 번호 시퀀스 생성 */
CREATE SEQUENCE SEQ_COMMENT_NO NOCACHE;

DROP SEQUENCE SEQ_COMMENT_NO;

/* 댓글 ("COMMENT") 테이블에 샘플 데이터 추가 */
BEGIN
	FOR I IN 1..2000 LOOP
		
		INSERT INTO "COMMENT"
		VALUES (SEQ_COMMENT_NO.NEXTVAL,
						SEQ_COMMENT_NO.CURRVAL || '번째 댓글',
						DEFAULT, DEFAULT,
						3,
						CEIL ( DBMS_RANDOM.VALUE(0, 2000) ),
						NULL -- 부모 댓글 번호
		);
	END LOOP;
END;

SELECT * FROM "COMMENT";

ALTER TABLE "COMMENT" DROP CONSTRAINT "FK_MEMBER_TO_COMMENT_1";

ALTER TABLE "BOARD" DROP CONSTRAINT "FK_MEMBER_TO_BOARD_1";




