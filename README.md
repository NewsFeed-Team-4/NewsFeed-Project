# 📱 SNS 프로젝트

![프로젝트 로고 또는 스크린샷](https://example.com/project-image.png)

간단한 SNS 기능을 제공하는 웹 애플리케이션입니다. 사용자 관리, 게시물 작성, 댓글, 좋아요, 팔로우 기능을 포함합니다.

---

## 🚀 기능 목록

- **사용자 관리**
  - 회원가입, 로그인, 프로필 조회 및 수정
- **게시물 관리**
  - 게시물 작성, 조회, 수정, 삭제
- **댓글 관리**
  - 댓글 작성, 조회, 수정, 삭제
- **친구 관리**
  - 친구 추가, 팔로우, 팔로워 목록 조회
- **좋아요 기능**
  - 게시물 및 댓글에 좋아요 추가/삭제

---

## 🛠 기술 스택

- **백엔드**: Spring Boot, Spring Data JPA, Spring Security
- **프론트엔드**: Thymeleaf (또는 React.js)
- **데이터베이스**: H2 (개발용), MySQL (운영용)
- **인증**: JWT (JSON Web Token)
- **빌드 도구**: Gradle
- **테스트**: JUnit, Mockito

---

## 🏃‍♂️ 실행 방법

### 1. 환경 설정
- Java 17 이상 설치
- MySQL 또는 H2 데이터베이스 설정


### 2. 프로젝트 구조
src
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           ├── config
│   │           ├── controller
│   │           ├── service
│   │           ├── repository
│   │           ├── entity
│   │           └── exception
│   └── resources
│       └── application.yml
└── test
    └── java
        └── com
            └── example
                ├── service
                └── controller
