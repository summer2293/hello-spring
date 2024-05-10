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
  - 템플릿 엔진. 값을 그대로 주는게 아닌 변수를 사용해서 값을 변경해서 줄 수 있음.
- view resolver
  - MVC 에서 컨트롤러가 비즈니스 로직 처리를 마친 후에 어떤 뷰로 응답을 생성할지 결정하는 역할 
  - 컨트롤러가 문자열 형태의 값을 반환하면, 해당 문자열을 템플릿 파일에 찾아 어떤 뷰로 응답을 생성할지 결정을 한다.
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
---
### view 생성하기
- 웰컴 페이지 만들기 
- src/main/resources/static 파일에 index.html 을 넣으면 spirng 에서 자동으로 정적페이지를 읽어 루트페이지를 생성한다.
- 웹 브라우저에서 /hello 로 던지면 스프링 부트는 톰캣이란 웹서버를 내장해서,
- @GetMapping("hello") 인 값을 찾는다.
- model data 값에 hello를 넣고
- return에서 "hello" 를 하는데, 이 hello는 resources/templates에서 파일을 찾는다.
-  <p th:text="'안녕하세요. ' + ${data}" >안녕하세요. 손님</p>
- 여기 값에 {data} 에 템플릿 엔진이 값을 넣어준다.

### 빌드
```
`./gradlew build`
`cd build/libs`
`java -jar hello-spring-0.0.1-SNAPSHOT.jar`
```
로 빌드할 수 있다.
서버 배포할 때는 이 파일만 복사해서 서버에 넣고 실행하면 서버에서 스프링이 동작하게 된다.

### REF
- https://sharonprogress.tistory.com/281