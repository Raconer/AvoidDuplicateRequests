# 중복 요청 방지

## 개요 

> 결제 및 쿠폰 발급 처리시 중복 요청으로 인한 서비스 오류 방지 목적으로 제작

1. JSP 미 완성

## 개발 방향

### 최초

- [x] 다중 테스트 및 부하 테스트
    - [x] PostMan Run Collection, Monitor 사용하여 테스트 
    - [x] Pinpoint 를 사용하여 monitor 확인 
- [x] @Transactional을 사용하여 sql 독립성
- [ ] Redis 사용으로 보상 Insert 및 Point Update 구현
    - [ ] Redis는 Queue와 같이 순차 적으로 데이터가 저장되므로 중복 보상 및 중복 포인트 방지

### 현재 까지 개발

> 회원 가입은 원래 Security에 설정 하여 Auth가 필요 하지만
> 테스트를 하기 위해 회원가입 Request를 Auth에서 제외 하였습니다.

> Redis를 아직 적용하지 못하였고 Transactional과 Query문을 최대한 사용해서 중복 방지를 하였지만 <br>
> 여러개의 서버를 사용 할 경우를 확인해 봐야 한다.<br>
> 이러한 경우 Redis를 사용하여 중복 처리를 적용 해야 한다.

```sql
    -- Insert Select를 사용하여 사용자 데이터, 전날 데이터 체크후 Insert
    INSERT INTO `reward` (`user_idx`, `reg_date`, `count`) 
    SELECT  #{userIdx}, 
            #{regDate}, 
            (CASE
                WHEN `r`.`count` IS NULL -- 전날 데이터가 없으면 1이 입력된다.
                    THEN 1
                ELSE IF( `r`.`count` >= 10, 1, `r`.`count`+ 1 ) -- 이전 데이터가 10이면 1, 미만 이면 +1을 한다.
            END ) as `count`
    FROM `user` `u`
    LEFT JOIN `reward` `r`
        ON `u`.`idx` = `r`.`user_idx`
        AND `r`.`reg_date` >= #{yesterDate} -- 등록된 보상이 전날 부터 데이터가 있으면 Join한다. Left Join이니 없으면 Null출력
    WHERE  `u`.`idx` =  #{userIdx} -- 요청한 사용자 Id
    AND (
        SELECT COUNT(`r1`.`idx`) 
        FROM `reward` `r1` 
        WHERE `r1`.`reg_date` >=  DATE_ADD(#{yesterDate}, INTERVAL 1 DAY)
        AND  `r1`.`user_idx` =  #{userIdx}
        ) = 0 -- Interval을 사용하여 오늘 등록 갯수가 0일때만 출력 하므로 등록된게 있으면 Insert를 못한다.
    LIMIT  1;
```

## Linux

### 설치

1. Mysql 설치
    * RDS를 사용 하여 연결 해도 되지만 비용이 많이 나오므로 EC2에 설치 하여사용
2. Java11 설치

### 설정

#### Mysql

1. 사용자 계정 생성 및 DB 접근 권한 설정
2. README.md에 등록된 DDL 셋팅

## Spec

> Bbuild.gradle 참고

## TEST용 SQL

```sql
    -- DB 생성
    CREATE DATABASE `redundancy`
    -- Table 생성

    -- user -> 사용자 및 로그인 정보 Table
    CREATE TABLE `user` (
        `idx` int NOT NULL AUTO_INCREMENT,
        `email` varchar(100) NOT NULL,
        `name` varchar(100) NOT NULL,
        `password` varchar(200) NOT NULL,
        `reg_date` datetime NOT NULL,
        `refresh_token` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    PRIMARY KEY (`idx`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

    -- user_data -> 사용자의 포인트 및 기타 정보를 가지고 있는 Table
    CREATE TABLE `user_data` (
        `idx` int NOT NULL AUTO_INCREMENT,
        `user_idx` int NOT NULL,
        `point` int NOT NULL DEFAULT '0',
    PRIMARY KEY (`idx`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

    -- Reward -> 출석 체크 및 사용자 연속 출석 여부 
    CREATE TABLE `reward` (
        `idx` int NOT NULL AUTO_INCREMENT,
        `user_idx` int NOT NULL,
        `reg_date` datetime NOT NULL,
        `count` int NOT NULL DEFAULT '0',
    PRIMARY KEY (`idx`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```