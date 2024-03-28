/*
    <GROUP BY>
        그룹 기준을 제시할 수 있는 구문
        여러 개의 값들을 하나의 그룹으로 묶어서 처리할 목적으로 사용한다.
*/

-- 전체 급여 총합 조회
SELECT SUM(SALARY)
FROM EMPLOYEE
;

-- 각 부서별 급여 총합 조회
SELECT DEPT_CODE, SUM(SALARY)
FROM EMPLOYEE
WHERE DEPT_CODE IS NOT NULL
GROUP BY DEPT_CODE
ORDER BY DEPT_CODE
;

-- 전체 사원 수 조회
SELECT COUNT(EMP_NAME)
FROM EMPLOYEE;

-- 각 부서별 사원 수 조회
SELECT DEPT_CODE, COUNT(EMP_NAME)
FROM EMPLOYEE
GROUP BY DEPT_CODE
;

-- 각 부서별 보너스를 받는 사원수
SELECT DEPT_CODE, COUNT(BONUS)
FROM EMPLOYEE
WHERE BONUS IS NOT NULL AND DEPT_CODE IS NOT NULL
GROUP BY DEPT_CODE
;

-- 각 직급별 급여 평균 조회
SELECT JOB_CODE, TRUNC(AVG(SALARY),1)
FROM EMPLOYEE
GROUP BY JOB_CODE
;

-- 부서별 사원수, 보너스를 받는 사원수, 급여의 합, 평균 급여, 최고 급여, 최저 급여 조회
SELECT 
    DEPT_CODE "부서 코드"
    , COUNT(*) "부서별 사원수"
    , COUNT(BONUS) "보너스를 받는 사원 수"
    , SUM(SALARY) "급여의 합"
    , FLOOR(AVG(SALARY)) "평균 급여"
    , MAX(SALARY) "최고 급여"
    , MIN(SALARY) "최저 급여"
FROM EMPLOYEE 
GROUP BY DEPT_CODE
ORDER BY DEPT_CODE
;

-- 성별 별 사원수 조회
SELECT SUBSTR(EMP_NO, 8, 1) AS "성별 코드",
       COUNT(*) AS "사원수"
FROM EMPLOYEE
GROUP BY SUBSTR(EMP_NO, 8, 1)
ORDER BY "성별 코드"
;

/*
    <HAVING>
        그룹에 대한 조건을 제시할 때 사용하는 구문(주로 그룹 함수의 결과를 가지고 비교 수행)
    
    * 실행 순서
        5: SELECT      조회하고자 하는 칼럼명 AS "별칭" | 계산식 | 함수식
        1: FROM        조회하고자 하는 테이블명
        2: WHERE       조건식
        3: GROUP BY    그룹 기준에 해당하는 칼럼명 | 계산식 | 함수식
        4: HAVING      그룹에 대한 조건식
        6: ORDER BY    정렬 기준에 해당하는 칼럼명 | 별칭 | 칼럼 순번
*/

-- 각 부서별 평균 급여 조회
SELECT 
    DEPT_CODE
    , FLOOR(AVG(SALARY)) || '원'
FROM EMPLOYEE
GROUP BY DEPT_CODE
;

-- 각 부서별 평균 급여 조회
SELECT 
    DEPT_CODE
    ,FLOOR(AVG(SALARY)) || '원' "평균 급여"
FROM EMPLOYEE
GROUP BY DEPT_CODE
HAVING AVG(SALARY) >= 3000000
;

-- 직급 별 총 급여의 합이 천만원 이상인 직급들만 조회
SELECT 
    JOB_CODE
    ,SUM(SALARY) || '원' "총 급여"
FROM EMPLOYEE
GROUP BY JOB_CODE
HAVING SUM(SALARY) >= 10000000
;

-- 부서별 보너스를 받는 사원이 없는 부서들만 조회
SELECT 
    DEPT_CODE
FROM EMPLOYEE
GROUP BY DEPT_CODE
HAVING COUNT(BONUS) LIKE 0
;

-- 보너스를 받지 않는 사원들을 대상으로
-- 부서별 평균 급여를 조회
-- (평귭급여가 300만원 이상인 부서는 제외)
-- (평균급여가 높은 순서대로 정렬)

SELECT 
    DEPT_CODE
    , FLOOR(AVG(SALARY)) "평균 급여"
FROM EMPLOYEE
WHERE BONUS IS NULL
GROUP BY DEPT_CODE 
HAVING AVG(SALARY) < 3000000
ORDER BY "평균 급여" DESC
;


/*
    <집계 함수>
        그룹별 산출한 결과 값의 중간 집계를 계산 해주는 함수
*/

-- 직급별 급여의 합계를 조회
SELECT JOB_CODE, SUM(SALARY)
FROM EMPLOYEE
GROUP BY JOB_CODE
ORDER BY JOB_CODE;

-- 마지막 행에 전체 총 급여의 합계까지 조회
SELECT JOB_CODE, SUM(SALARY)
FROM EMPLOYEE
GROUP BY ROLLUP(JOB_CODE)
ORDER BY JOB_CODE;

SELECT JOB_CODE, SUM(SALARY)
FROM EMPLOYEE
GROUP BY CUBE(JOB_CODE)
ORDER BY JOB_CODE;

-- 부서 코드도 같고 직급 코드도 같은 사원들을 그룹 지어서 급여의 합계를 조회
SELECT DEPT_CODE, JOB_CODE, SUM(SALARY)
FROM EMPLOYEE
GROUP BY DEPT_CODE, JOB_CODE
ORDER BY DEPT_CODE, JOB_CODE;

-- ROLLUP(컬럼 1, 컬럼 2, ...) -> 컬럼 1을 가지고 중간집계를 내는 함수
SELECT DEPT_CODE, JOB_CODE, SUM(SALARY)
FROM EMPLOYEE
GROUP BY ROLLUP(DEPT_CODE, JOB_CODE)
ORDER BY DEPT_CODE, JOB_CODE;

-- CUBE(컬럼 1, 컬럼 2, ...) -> 컬럼 1을 가지고 중간집계를 내고, 컬럼 2를 가지고 중간집계를 낸다. 즉, 전달되는 컬럼 모두 집계하는 함수
SELECT DEPT_CODE, JOB_CODE, SUM(SALARY)
FROM EMPLOYEE
GROUP BY CUBE(DEPT_CODE, JOB_CODE)
ORDER BY DEPT_CODE, JOB_CODE;



/*
    <집합 연산자>
        여러 개의 쿼리문을 가지고 하나의 쿼리문으로 만드는 연산자이다.
        
        UNION       : 두 쿼리문을 수행한 결과값을 더한 후 중복되는 행은 제거한다. (합집합)
        UNION ALL   : UNION과 동일하게 두 쿼리문을 수행한 결과값을 더하고 중복은 허용한다. (합집합)
        INTERSECT   : 두 쿼리문을 수행한 결과값에 중복된 결과값만 추출한다. (교집합)
        MINUS       : 선행 쿼리의 결과값에서 후행 쿼리의 결과값을 뺀 나머지 결과값만 추출한다. (차집합)
*/

-- 1. 부서코드
SELECT EMP_ID,EMP_NAME, DEPT_CODE, SALARY
FROM EMPLOYEE
WHERE DEPT_CODE = 'D5'
;

-- 2. 급여 300 초가
SELECT EMP_ID,EMP_NAME, DEPT_CODE, SALARY
FROM EMPLOYEE
WHERE SALARY > 3000000
;

-- 1번 + 2번 
-- 1. 부서코드
SELECT 
    EMP_ID
    , EMP_NAME
    , DEPT_CODE
    , SALARY
FROM EMPLOYEE
WHERE DEPT_CODE = 'D5'
MINUS
SELECT 
    EMP_ID
    , EMP_NAME
    , DEPT_CODE
    , SALARY
FROM EMPLOYEE
WHERE SALARY > 3000000
;












