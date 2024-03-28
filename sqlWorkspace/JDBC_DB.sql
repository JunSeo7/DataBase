-- MEMBER 테이블의 NO 칼럼에 사용할 시퀀스
DROP SEQUENCE SEQ_NUMBER_NO;
CREATE SEQUENCE SEQ_NUMBER_NO NOCYCLE NOCACHE;
-- BOARD 테이블의 NO 칼럼에 사용할 시퀀스
DROP SEQUENCE SEQ_BOARD_NO;
CREATE SEQUENCE SEQ_BOARD_NO NOCYCLE NOCACHE;

-- MEMBER 테이블 생성 (NO, ID, PWD, NICK, ENROLL_DATE, QUIT_YN)
DROP TABLE MEMBER;
CREATE TABLE MEMBER(
    NO              NUMBER
    , ID            VARCHAR2(100)
    , PWD           VARCHAR2(100)
    , NICK          VARCHAR2(100)
    , ENROLL_DATE   TIMESTAMP
    , QUIT_YN       CHAR(1)
)
;

SELECT *
FROM MEMBER
;

-- 회원가입
INSERT INTO MEMBER(NO, ID, PWD, NICK, ENROLL_DATE, QUIT_YN) 
VALUES(SEQ_NUMBER_NO.NEXTVAL, 'USER01' , '1234', 'NICK01' , SYSDATE, 'N')
;
COMMIT;

INSERT INTO MEMBER(NO, ID, PWD, NICK, ENROLL_DATE, QUIT_YN) 
VALUES('시퀀스를이용한 값', ? , ?, ? , SYSDATE, 'N')
; -- 가입일시, 탈퇴여부는 항상 고정이므로 사용자한테 받을 필요 없음
COMMIT;


-- 로그인
SELECT *
FROM MEMBER
WHERE ID = 'USER01'
AND PWD = '1234'
;

SELECT *
FROM MEMBER
WHERE ID = ?
AND PWD = ?
;

-- 회원 정보 수정 (비번 변경)
UPDATE MEMBER
    SET
        PWD = '0000'
    WHERE NO = 1
;
COMMIT;

UPDATE MEMBER
    SET
        PWD = ?
    WHERE NO = ?
;
COMMIT;
        


-- 회원 정보 수정 (닉네임 변경)
UPDATE MEMBER
    SET
        NICK = '김수정'
    WHERE NO = 1
;
COMMIT;

UPDATE MEMBER
    SET
        NICK = ?
    WHERE NO = ?
;
COMMIT;

-- 회원 탈퇴
DELETE MEMBER
WHERE NO = 1;
COMMIT;

DELETE MEMBER
WHERE NO = ?;
COMMIT;

UPDATE MEMBER 
    SET QUIT_YN = 'Y'
WHERE NO = 1;
COMMIT;

UPDATE MEMBER 
    SET QUIT_YN = 'Y'
WHERE NO = ?;
COMMIT;

------------------------------------------------------

-- BOARD 테이블 생성 (NO, TITLE, CONTENT, WRITER, ENROLL_DATE, DEL_YN)
DROP TABLE BOARD;
CREATE TABLE BOARD(
    NO              NUMBER
    , TITLE         VARCHAR2(100)
    , CONTENT       VARCHAR2(4000)
    , WRITER        NUMBER
    , ENROLL_DATE   TIMESTAMP
    , DEL_YN        CHAR(1)
);

-- 게시글 작성
INSERT INTO BOARD(NO, TITLE, CONTENT, WRITER, ENROLL_DATE, DEL_YN)
VALUES(SEQ_BOARD_NO.NEXTVAL , '제목01' , '내용01' , 1 , SYSDATE, 'N');
COMMIT;

INSERT INTO BOARD(NO, TITLE, CONTENT, WRITER, ENROLL_DATE, DEL_YN)
VALUES(시퀀스이용한번호 , ? , ? , ? , SYSDATE, 'N');
COMMIT;

-- 게시글 목록 조회 (최신순)
SELECT *
FROM BOARD
ORDER BY NO DESC
;

-- 게시글 상세 조회 (게시글 번호 이용해서)
SELECT *
FROM BOARD
WHERE NO = 1
;

SELECT *
FROM BOARD
WHERE NO = ?
;

-- 게시글 검색 (제목 검색)
SELECT *
FROM BOARD
WHERE TITLE LIKE '%제목%'
;


SELECT *
FROM BOARD
WHERE TITLE LIKE '%?%';
;

-- 게시글 검색 (내용 검색)
SELECT *
FROM BOARD
WHERE CONTENT LIKE '%ㅋㅋ%';
;

SELECT *
FROM BOARD
WHERE CONTENT LIKE '%?%';
;

-- 게시글 수정 (제목 수정 - 게시글 번호 이용해서)
UPDATE BOARD
    SET
        TITLE = '수정된제목ㅋㅋ'
WHERE NO = 1
;
COMMIT;

UPDATE BOARD
    SET
        TITLE = ?
WHERE NO = ?
;
COMMIT;

-- 게시글 수정 (내용 수정 - 게시글 번호 이용해서)
UPDATE BOARD
    SET
        CONTENT = '수정된내용ㅋㅋ'
WHERE NO = 1
;
COMMIT;

UPDATE BOARD
    SET
        CONTENT = ?
WHERE NO = ?
;
COMMIT;



-- 게시글 삭제 (번호 이용해서)
DELETE BOARD
WHERE NO = 1
;
COMMIT;

DELETE BOARD
WHERE NO = ?
;
COMMIT;

