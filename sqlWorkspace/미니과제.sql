-- BOARD ���̺��� NO Į���� ����� ������ ����
DROP SEQUENCE SEQ_BOARD_NO;
-- BOARD ���̺��� NO Į���� ����� ������ ����
CREATE SEQUENCE SEQ_BOARD_NO NOCACHE NOCYCLE;

-- BOARD ���̺� ���� ����
DROP TABLE BOARD CASCADE CONSTRAINTS;
-- BOARD ���̺� ���� (NO , TITLE, CONTENT, ENROLL_DATE) // ENROLL_DATE �� DEFAULT ������ ����ð�
CREATE TABLE BOARD(
    NO              NUMBER
    , TITLE         VARCHAR2(100)
    , CONTENT       VARCHAR2(4000)
    , ENROLL_DATE   TIMESTAMP       DEFAULT SYSDATE
);

-- �Խñ� �ۼ�
INSERT INTO BOARD(NO, TITLE, CONTENT) VALUES(SEQ_BOARD_NO.NEXTVAL , '����111' , '����111');
INSERT INTO BOARD(NO, TITLE, CONTENT) VALUES(SEQ_BOARD_NO.NEXTVAL , ? , ?);

-- �Խñ� ���� (���� ���� - ��ȣ�̿��ؼ�)
UPDATE BOARD SET TITLE = '���������񤻤�' WHERE NO = 1;
UPDATE BOARD SET TITLE = ? WHERE NO = ?;

-- �Խñ� ���� (���� ���� - ��ȣ�̿��ؼ�)
UPDATE BOARD SET CONTENT = '�����ȳ��뤻��' WHERE NO = 1;
UPDATE BOARD SET CONTENT = ? WHERE NO = ?;

-- �Խñ� ���� (��ȣ�̿��ؼ�)
DELETE BOARD WHERE NO = 1;
DELETE BOARD WHERE NO = ?;

-- �Խñ� ��� ��ȸ (�ֽż�)
SELECT 
    NO, 
    TITLE
    , TO_CHAR(ENROLL_DATE, 'MM/DD HH:MI') ENROLL_DATE
FROM BOARD 
ORDER BY NO DESC;

-- �Խñ� �� ��ȸ (��ȣ �̿��ؼ�)
SELECT * FROM BOARD WHERE NO = 1;
SELECT * FROM BOARD WHERE NO = ?;

-- �Խñ� �˻� (��������)
SELECT * FROM BOARD WHERE TITLE LIKE '%�ȳ�%';
SELECT * FROM BOARD WHERE TITLE LIKE '%' || ? || '%';

-- �Խñ� �˻� (��������)
SELECT * FROM BOARD WHERE CONTENT LIKE '%�ȳ�%';
SELECT * FROM BOARD WHERE CONTENT LIKE '%?%';

-- Ŀ��
COMMIT;