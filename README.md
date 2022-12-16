# 중복 요청 방지

## 개요 

> 결제 및 쿠폰 발급 처리시 중복 요청으로 인한 서비스 오류 방지 목적으로 제작

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