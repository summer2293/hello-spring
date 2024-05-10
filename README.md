# Spring 공부

## chap 01. 프로젝트 생성
알아볼 것 
- 스프링을 왜 쓰는가?
- IOC
- DI
- AOP
- Spring Boot
- Gradle
  - java complie/test/packaging 등을 자동화 시켜주는 도구 
  - build.gradle에 파일에서 java, spring, 라이브러리등의 config 설정을 하면 알아서 해당 프로젝트를 빌드해주는 것 같다.
- JPA
- Hibernate
- Thymeleaf
----
### 기본 라이브러리
spring-boot-starter-web
spring-boot-starter-tomcat: 톰캣 (웹서버)
spring-webmvc: 스프링 웹 MVC 
spring-boot-starter-thymeleaf: 타임리프 템플릿 엔진(View)
spring-boot-starter(공통): 스프링 부트 + 스프링 코어 + 로깅
    spring-boot spring-core
    spring-boot-starter-logging logback, slf4j
        - slf4j : 인터페이스, logtack: 구현체
spring-boot-starter-test
- junit: 테스트 프레임워크
- mockito: 목 라이브러리
- assertj: 테스트 코드를 좀 더 편하게 작성하게 도와주는 라이브러리 spring-test: 스프링 통합 테스트 지원