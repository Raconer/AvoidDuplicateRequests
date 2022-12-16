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

### Server

1. Spring Boot application-default.yml에 실서버 정보 변경
    1. 현재는 application-default.yml을 사용하나 prod, dev 등등 배포 전략에 따라 변경 될수 있다.
1. Spring Boot Build하여 jar 파일 생성
1. FileZilla를 사용하여 jar linux 서버에 전송
    1. 실제 운영을 위해서는 Jenkins를 사용하여 설정
3. java -jar avoid.jar // avoid.jar말고 이름이 변경될수있다.
    1. -Dspring.profiles.active=prod 설정으로 yml을 구별 할수있다.

### AWS

1. EC2 Linux로 생성하여 상단 설정 및 Server 설정을 한다.
    1. 보안 그룹을 설정 하여 외부 접근 허용 0.0.0.0/0, ::/0
    1. 아웃 바운드도 설정하여 Response 허용
1. Route53을 사용하여 Domain Setting


## Spec

> Bbuild.gradle 참고

## 프로젝트 구조

> 테스트는 주로 PostMan에서 실행 하였고
> "src.test"에 기본적으로 작성 하였으나 Bearer 및 좀더 구체적인 코드 수정이 필요 합니다.
> 지금 프로젝트 내에서 테스트는 .http로 테스트 하면 됩니다.
> 
> controller, service, mapper를 기준으로 프로젝트를 구성하지 않고
> ```
> ├─ api 
> │  ├─ controller
> │  │  └─ RewardController.java
> │  ├─ service
> │  │  └─ RewardService.java
> │  ├─ mapper
> │  │  └─ RewardMapper.java
> ```
> sign, user, reward 및 하나의 큰 Request 기준으로 프로젝트가 구성 되어 있습니다.
> ```
>├─ api 
>│  ├─ reward 
>│  │  ├─ dto
>│  │  ├─ mapper
>│  │  ├─ RewardController.java
>│  │  └─ service
> ```

```
avoid
├─ .http // 테스트 Request 
├─ gradlew // dependency 정의
├─ README.md // 현재 파일
└─ src
   ├─ main
   │  ├─ java.com.duplicate.requests.avoid
   │  │                                ├─ api 
   │  │                                │  ├─ reward // 포인트 
   │  │                                │  │  ├─ dto
   │  │                                │  │  │  ├─ RewardDto.java
   │  │                                │  │  │  └─ RewardInfoDto.java
   │  │                                │  │  ├─ mapper
   │  │                                │  │  │  └─ RewardMapper.java
   │  │                                │  │  ├─ RewardController.java
   │  │                                │  │  └─ service
   │  │                                │  │     └─ RewardService.java
   │  │                                │  ├─ sign // 로그인, 회원가입, 토큰 생성
   │  │                                │  │  ├─ dto
   │  │                                │  │  │  ├─ AuthDto.java
   │  │                                │  │  │  └─ SignDto.java
   │  │                                │  │  ├─ service
   │  │                                │  │  │  └─ SignService.java
   │  │                                │  │  └─ SignController.java
   │  │                                │  ├─ user // 사용자 
   │  │                                │  │  ├─ dto
   │  │                                │  │  │  ├─ AccountDto.java
   │  │                                │  │  │  └─ UserDto.java
   │  │                                │  │  ├─ mapper
   │  │                                │  │  │  └─ UserMapper.java
   │  │                                │  │  ├─ service
   │  │                                │  │  │  └─ UserService.java
   │  │                                │  │  └─ UserController.java // 테스트용 User List 출력
   │  │                                │  └─ userData // User Data
   │  │                                │     ├─ dto
   │  │                                │     │  └─ UserDataDto.java
   │  │                                │     ├─ mapper
   │  │                                │     │  └─ UserDataMapper.java
   │  │                                │     └─ service
   │  │                                │        └─ UserDataService.java
   │  │                                ├─ AvoidApplication.java
   │  │                                ├─ common // 공통 코드 및 Model
   │  │                                │  ├─ code
   │  │                                │  │  └─ ValidCode.java
   │  │                                │  └─ model
   │  │                                │     ├─ DefDataResponse.java
   │  │                                │     ├─ DefResponse.java
   │  │                                │     └─ valid
   │  │                                │        └─ Validate.java
   │  │                                ├─ config // Spring Security Setting 
   │  │                                │  └─ Security.java
   │  │                                ├─ filter // Token Filter
   │  │                                │  ├─ JwtAuthenticationEntryPoint.java
   │  │                                │  └─ JwtRequestFilter.java
   │  │                                └─ utils // 공통 Utils
   │  │                                   ├─ JwtUtil.java
   │  │                                   ├─ PasswordUtil.java
   │  │                                   └─ ValidErrUtil.java
   │  └─ resources
   │     ├─ application-default.yml // Spring Properties
   │     ├─ log4jdbc.log4j2.properties // sql log setting
   │     ├─ logback.xml
   │     └─ META-INF
   │        └─ mybatis // SQL 문 
   │           ├─ mapper
   │           │  ├─ RewardMapper.xml 
   │           │  ├─ UserDataMapper.xml
   │           │  └─ UserMapper.xml
   │           └─ mybatis-config.xml
   └─ test // 테스트 코드
      └─ java
         └─ com
            └─ duplicate
               └─ requests
                  └─ avoid
                     ├─ api
                     │  ├─ reward
                     │  │  └─ RewardControllerTest.java
                     │  └─ sign
                     │     ├─ SignControllerTest.java
                     │     └─ SignServiceTest.java
                     └─ AvoidApplicationTests.java
```

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