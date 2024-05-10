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
#### 기본 라이브러리
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
#### view 생성하기
- 웰컴 페이지 만들기 
- src/main/resources/static 파일에 index.html 을 넣으면 spirng 에서 자동으로 정적페이지를 읽어 루트페이지를 생성한다.
- 웹 브라우저에서 /hello 로 던지면 스프링 부트는 톰캣이란 웹서버를 내장해서,
- @GetMapping("hello") 인 값을 찾는다.
- model data 값에 hello를 넣고
- return에서 "hello" 를 하는데, 이 hello는 resources/templates에서 파일을 찾는다.
-  <p th:text="'안녕하세요. ' + ${data}" >안녕하세요. 손님</p>
- 여기 값에 {data} 에 템플릿 엔진이 값을 넣어준다.

#### 빌드
```
`./gradlew build`
`cd build/libs`
`java -jar hello-spring-0.0.1-SNAPSHOT.jar`
```
로 빌드할 수 있다.
서버 배포할 때는 이 파일만 복사해서 서버에 넣고 실행하면 서버에서 스프링이 동작하게 된다.

## chap 02. 스프링 웹 개발 기초
웹 개발 방법
- 정적 컨텐츠
  - 파일 그대로 전달하는 방식
  - resources/static 에 파일을 넣으면 된다.
  - 요청이 들어오면 내장 서버가 일단 controller 를 찾고, 없다면 static 쪽 파일이 있는지 확인 후 맵핑해서 내려준다.
- MVC와 템플릿 엔진
  - 서버에서 프로그래밍해서 HTML파일을 동적으로 내려주는 것
  - view resolver 가 controller 가 반환한 템플릿 이름과 일치하는 템플릿을 찾고, 템플릿 엔진이 값을 렌더링 후 html 전달
- API
  - HTML 없이 json 데이터 포맷으로 전달, ResponseBody 어노테이션을 사용한다.
  - response body 를 사용하면 톰캣 내장 서버에서 컨테이너에 던짐
  - responsebody annotation 이 붙어있지 않으면, view resolver 에게 던지는데
  - responsebody 가 있음 httpmessage converter 가 동작한다.
  - 객체면 jsonconverter, 문자면 stringconverter이 동작한다. 
  - jsonconverter는 (MappingJackson2HttpMessageConverter) 라이브러리 사용
  - http 에 응답을 던져야 겠다고 넘긴다.
  - 객체를 줄 경우에는 스프링 입장에서는 json 방식으로 만들어서 기본으로 http 응답에 반환하는게 기본 정책이다.
  - 클라이언트의 HTTP Accept 해더와 서버의 컨트롤러 반환 타입 정보 둘을 조합해서 반환한다.

## chap 03. 비즈니스 요구사항 정리
#### 비즈니스 요구사항 정리
- 데이터: 회원ID, 이름
- 기능: 회원 등록, 조회
- 아직 데이터 저장소가 선정되지 않음

- 컨트롤러: mvc 컨트롤러 역할
- 서비스: 핵심 비즈니스 로직을 구현한다
- 레포지토리: 데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리
- 도메인: 비즈니스 도메인 객체, 데이터 모델 같은건가.. 데이터베이스에 저장하고 관리되는 객체 

#### 회원 도메인과 리포지토리 만들기
- 도메인을 만듬. 데이터베이스 맵핑하는 느낌인 것 같다.
- 해당 도메인을 객체로 가지는 레포지토리 인터페이스를 만든다.
- 관련 구현체를 만들어 사용하고자 하는 db나 관련된 값으로 구현한다

#### 회원 레포지토리 테스트 케이스 작성
- 웹 앱의 컨트롤러를 실행해보면서 테스트 하면 너무 오래걸림.
- 반복적으로 테스트를 실행하기 어려워서 Junit이라는 testcode framework 로 테스트를 만들어 실행한다.
- 테스트의 순서는 보장이 안된다. 따라서 다른 객체가 저장이 되어 다른 객체가 나와버렸다.
- 따라서 테스트가 끝나면 깔끔하게 테스트를 종료시켜야한다.
- 테스트를 만들고 검증을 하는 방법 -> TDD 테스트 주도 개발

### REF
- https://sharonprogress.tistory.com/281