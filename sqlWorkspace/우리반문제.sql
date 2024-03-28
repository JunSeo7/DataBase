-- 남자 직원들의 이름과 나이를 나이가 많은 순으로 조회
-- 이름은 성을 제외, 나이는 만으로 계산
-- EX 동운 61세
--    길동 54세 

SELECT 
    REPLACE(EMP_NAME,(SUBSTR(EMP_NAME,2,1)),'*') AS 이름
    , EXTRACT(YEAR FROM SYSDATE) - (19 || SUBSTR(EMP_NO,1,2)) - 1 || '세' AS 나이
FROM EMPLOYEE
WHERE SUBSTR(EMP_NO,8,1) = 1
ORDER BY EMP_NO
;
-- 강사님
-- 보너스를 받지 않는 사원들을 대상으로
-- 부서별 평균 급여를 조회
-- (평귭급여가 300만원 이상인 부서는 제외)
-- (평균급여가 높은 순서대로 정렬)

SELECT 
    DEPT_CODE
    , FLOOR(AVG(SALARY)) AS 평균급여
FROM EMPLOYEE
WHERE BONUS IS NULL
GROUP BY DEPT_CODE
HAVING AVG(SALARY) < 3000000
ORDER BY AVG(SALARY) DESC
;

-- 김송희님
-- 연봉이 3000만원 이하인 직원의 사번,이름,연봉 조회
SELECT 
    EMP_ID
    , EMP_NAME
    , SALARY * 12 AS 연봉
FROM EMPLOYEE
WHERE SALARY * 12 <= 30000000
ORDER BY 연봉 DESC
;

-- 김제민님
-- D5부서의 사원 이름, 직급, 월급 출력
SELECT EMP_NAME, JOB_CODE, SALARY
FROM EMPLOYEE
WHERE DEPT_CODE IN ('D5')
ORDER BY SALARY
;
-- 사원들중 월급이 가장 많은 사람과 가장 적은 사람의 연봉 차이 
SELECT MAX(SALARY*12) - MIN(SALARY*12) AS 연봉차이
FROM EMPLOYEE
WHERE DEPT_CODE IN ('D5')
ORDER BY SALARY
;

-- 김태우님
-- dept_code가 D5인 사람들의 이름과 휴대번호 앞자리 3자리를 출력하시오
SELECT EMP_NAME, SUBSTR(PHONE,1,3)
FROM EMPLOYEE
WHERE DEPT_CODE IN ('D5')
;

-- 나지원님
-- 보너스를 받는 사원을 대상으로 
-- 직급별 평균 급여 조회 
-- 평균급여가 높은 순서대로 정렬
-- (단, 퇴직하지 않은 사원들 대상 조회)
SELECT 
    JOB_CODE
    , FLOOR(AVG(SALARY)) AS 평균급여
FROM EMPLOYEE
WHERE ENT_YN LIKE 'N'
GROUP BY JOB_CODE
ORDER BY AVG(SALARY) DESC
;

-- 문태웅님
-- 부서중에 급여 합계가 제일 적은 부서의 급여합계는?
-- (단, EMPLOYEE 계정에서 풀어야한다.)
-- // 이것도 하나만 출력하게 해야 되는데 잘 모르겠음
SELECT 
    SUM(SALARY)
FROM EMPLOYEE
GROUP BY DEPT_CODE 
ORDER BY SUM(SALARY) DESC
;

-- 박근아님
-- 보너스를 받는 사원들의 모든 코드를 조회
-- 대신 급여가 3000000원 이상인 사람들만
SELECT *
FROM EMPLOYEE
WHERE BONUS IS NOT NULL
    AND SALARY >= 3000000;
    
-- 박진동님
-- 부서별 직급이 높고 연봉이 많은순으로 이름, 사번, 연봉, 조회
SELECT 
    EMP_NAME
    , EMP_ID
    , SALARY
FROM EMPLOYEE
ORDER BY JOB_CODE ASC, SALARY
;

-- 백의현님
-- 010으로 시작하지 않는 전화번호를 가진 사람들의 이름, 부서, 전화번호 
-- (NULL 포함 X, 부서 코드로 오름차순)
SELECT
    DEPT_CODE
    , PHONE
    , DEPT_TITLE
FROM EMPLOYEE, DEPARTMENT
WHERE PHONE NOT LIKE ('010%') AND DEPT_CODE IS NOT NULL
ORDER BY DEPT_CODE
;

-- 소현지님
-- 부서 평균 급여가 400만원 이상인 부서만 조회(소수점 나올시 소수점 이하는 버리기)
SELECT
    DEPT_CODE
    , AVG(SALARY)
FROM EMPLOYEE
GROUP BY DEPT_CODE
HAVING AVG(SALARY) >= 4000000
;

-- 손지욱님
-- 보너스를 받는 사원들을 대상으로
-- 부서별 평균 보너스 조회
SELECT 
    DEPT_CODE
    , (AVG(BONUS)) AS 평균보너스
FROM EMPLOYEE
WHERE BONUS IS NOT NULL
GROUP BY DEPT_CODE
;

-- 송예린님
-- 여자 사원을 대상으로
-- 직급별 전체 급여를 조회 (별칭 : 급여총액)
-- 직급코드 J4 제외
-- 급여합이 낮은 순서대로 정렬
SELECT 
    JOB_CODE
    ,SUM(SALARY) 급여총액
FROM EMPLOYEE
WHERE SUBSTR(EMP_NO, 8, 1) = 2
GROUP BY JOB_CODE
HAVING JOB_CODE != 'J4'
ORDER BY "급여총액" ASC;

-- 신은지님
-- 부서코드별 사원아이디 번호가 가장 빠른사람의 부서코드와 사원아이디 조회
--(보너스가 있는 사람은 제외)
SELECT 
    DEPT_CODE
    , MIN(EMP_ID)
FROM EMPLOYEE
WHERE BONUS IS NULL
GROUP BY DEPT_CODE
ORDER BY DEPT_CODE
;

-- 윤혜연님
-- 관리자 사번이 있는 사원들 대상으로
-- 부서별 총 급여를 조회
-- (급여 높은 순서대로 정렬)
SELECT DEPT_CODE, SUM(SALARY)
FROM EMPLOYEE
WHERE MANAGER_ID IS NOT NULL
GROUP BY DEPT_CODE
ORDER BY SUM(SALARY) DESC
;

-- 병신
-- 1. 사원번호 200번인 직원의 이름과, 직급코드 출력
SELECT EMP_NAME, JOB_CODE
FROM EMPLOYEE
WHERE EMP_ID = 200
;

-- 2. 회사의 사장인 선동일은 성이 '이'씨인 사원의 모든 성을 자신과 동일한 '선'씨로 개명시키려고 한다. 
--    선동일에게 잘 보이기 위해 개명을 시켜보자
SELECT REPLACE(EMP_NAME,'이%','선')
FROM EMPLOYEE
;

-- 3. 기뻐한 선동일은 이름을 개명한 직원들의 직급을 하나씩 높이려고 한다.
SELECT 
    REPLACE(EMP_NAME,'이%','선')
    , 'J' || (SUBSTR(JOB_CODE,2,1) - 1)
    , JOB_CODE
FROM EMPLOYEE
WHERE EMP_NAME LIKE '이%'
;

-- 이수인님
-- 입사년도가 같은 사람들의 평균급여를 보고 250만원보다 적은 사람들에게 교육을 시키려 한다
-- 이사람들의 입사년도와 평균 급여를 조회하라
SELECT SUBSTR(HIRE_DATE,1,2), AVG(SALARY)
FROM EMPLOYEE
GROUP BY SUBSTR(HIRE_DATE,1,2)
HAVING AVG(SALARY) < 2500000
ORDER BY SUBSTR(HIRE_DATE,1,2) DESC
;

-- 이용진님
--사원이름과 현재 나이 출력
SELECT 
    EMP_NAME
    , EXTRACT(YEAR FROM SYSDATE) - (19 || SUBSTR(EMP_NO,1,2)) - 1 || '세' AS 나이
FROM EMPLOYEE
ORDER BY 나이 DESC
;

--부서별 최고급여 조회
SELECT
    DEPT_CODE
    , MAX(SALARY)
FROM EMPLOYEE
GROUP BY DEPT_CODE
ORDER BY DEPT_CODE
;

-- 이주형님
-- 기준년도는 2017년도이다 
-- 연차가 5년 미만이고 직급이 대리인 직원의 사번, 이름, 연차, 직급 조회
SELECT EMP_ID, EMP_NAME,  2017 - TO_CHAR(HIRE_DATE, 'RRRR') 연차, JOB_CODE
FROM EMPLOYEE
WHERE TO_CHAR(HIRE_DATE, 'RRRR') > 2012 AND JOB_CODE ='J6';

-- 이지수님
-- 직급별 평균 급여가 200만원 이상인 부서에서 전화번호 4번째 자리가 3인 직급코드만 조회
-- 추가설명 : (010-X1234-567) 여기서 X가 3인 사람
SELECT JOB_CODE
FROM EMPLOYEE
WHERE SUBSTR(PHONE, 5, 1) = '3'
GROUP BY JOB_CODE
HAVING AVG(SALARY) >= 2000000
;

-- 장민정님
--70년생 이상인 사원들을 대상으로 한 부서별 평균급여를 소수점 아래를 제외하고 구하시오
--단, 70년생 이후 출생인 사원이 1명인 부서는 제외
--출력 헤더는 부서코드,인원,평균급여 
SELECT DEPT_CODE 부서코드, COUNT(*)인원, FLOOR(AVG(SALARY)) 평균급여
FROM EMPLOYEE
WHERE SUBSTR(EMP_NO,1,2) > '70'--70년생 이상인 사원들을 대상으로
GROUP BY DEPT_CODE
HAVING COUNT(*)>1
;


-- 장종찬님
-- 남성사원, 여성사원 사원 수 구하기
SELECT SUBSTR(EMP_NO,8,1),COUNT(*)  
FROM EMPLOYEE
GROUP BY SUBSTR(EMP_NO,8,1);

-- 주선기님
-- 회사 채용 관련한 시스템을 개편하려고 한다.
-- 그렇기 위해서는 우선, 입사지원하는 나잇대를 내부적으로 파악해야한다.
-- 직원들 각각의 입사나이를 나이가 적은 순서대로 보여라. (2023년도까지의 한국나이로 계산)
SELECT
    EMP_NAME 사원명
    , TO_NUMBER(TO_CHAR(HIRE_DATE, 'RRRR')) - TO_NUMBER('19' || SUBSTR(EMP_NO, 1, 2)) 입사나이
    , TO_NUMBER(TO_CHAR(SYSDATE, 'RRRR')) - TO_NUMBER('19' || SUBSTR(EMP_NO, 1, 2)) 현재나이
    , '19' || SUBSTR(EMP_NO, 1, 2) 생년
    , TO_CHAR(HIRE_DATE, 'RRRR') 입사년도
FROM EMPLOYEE
ORDER BY 입사나이 ASC
;


-- 최우성님
-- 퇴직자가 있는 부서코드 , 그 사람 이름 과 연봉을 구하시오
SELECT DEPT_CODE , EMP_NAME , SALARY * 12 연봉
FROM EMPLOYEE
WHERE ENT_YN  = 'Y';

-- 보너스를 받는 부서코드, 평균 급여를  조회하시오(평균급여가 높은 순서대로)
SELECT 
    DEPT_CODE 
    , AVG(SALARY)
FROM EMPLOYEE
WHERE BONUS IS NOT NULL
GROUP BY DEPT_CODE
ORDER BY AVG(SALARY) DESC;

-- 한재두님
-- 10월에 생일인 사람들을 조회해서
-- 급여의 50%만큼 보너스를 줄려고한다
-- 해당 대상자들과 얼마의 보너스를 주는지 조회하시요

SELECT EMP_NAME 이름
    ,EMP_NO 생일
    ,SALARY 급여
    ,SALARY * 0.5 AS 보너스
    FROM EMPLOYEE
WHERE EMP_NO LIKE '__10%';

-- 홍주영님
-- 각 직급별로 평균 연봉(소수점없이 반올림)과 보너스를 받는 인원수 조회
SELECT 
    JOB_CODE
    , ROUND(AVG(SALARY))
    , COUNT(BONUS)
FROM EMPLOYEE
GROUP BY JOB_CODE
;


