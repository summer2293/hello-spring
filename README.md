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

#### 회원 서비스 개발 및 회원 서비스 테스트
- 서비스는 레포지토리보다는 비지니스 로직에 좀 더 가깝게 개발한다.
- 멤버 서비스에 있는 값은 new 다른 객체가 될 경우 내용물이 달라질 수 있다.
- 그래서 멤버 메모리 리포지토리랑, 테스트 멤버 메모리 리포지토리가 다를 수도 있다.
- 같은걸로 테스트해야하는데, 다른 객체로 해야하는 문제가 있다. 이걸 해결하기 위해 컨스트럭터로 바꾸고 외부에서 의존성 주입을 한다
- 이것을 DI 의존성 주

## chap 04. 스프링 빈과 의존관계
#### 컴포넌트 스캔과 자동 의존관계 설정
- 매 클래스마다 new 해서 쓰면 새 객체가 생성되어 기존 값이랑 안맞는 경우가 있다.
- 따라서 매 클래스마다 만들지 않고, 한번만 생성해서 싱글톤으로 만들고 공유하는 방식을 사용한다.
- 스프링어플리케이션이 실행할때 관련 싱글톤 객체들을 생성해서 가지고 있고, 의존관계를 직접 설정해주는 방식이 있다.
- 이걸 스프링에서 의존성 주입을 해주는 방식이라고 한다.
- 대표적으로 컴포넌트 스캔과 자바 빈에서 등록하는 방법이 있다.
- 컴포넌트 스캔은 컴포넌트 어노테이션이 있으면 스프링 앱이 띄울때 하나하나 읽어 해당 패키지 내의 컴포넌트를 읽고 객체를 생성하는 방식이다.
- @Service, @Controller, @Repository 안에도 @Component 메서드가 들어있다.
- 해당 컴포넌트를 걸고, @autowired를 걸면 스프링이 자동으로 내부 값을 맵핑 시켜준다.

#### 자바 코드로 직접 스프링 빈 등록하기
- DI 에는 필드, 셋터, 생성자 주입이 있다.
- 현재는 생성자주입. 생성자를 통해 의존성 주입을 함
- 필드에다가 @Autowired를 하면 바꿀 수 있는 방법이 없다.
- Setter 주입. 이 경우에는 public 이 되어야함. 중간에 바꾸면 문제가 생긴다.
- 로딩시점에 조립 이후에는 바뀔 일이 없다.
- 직접 사용하면 빈에서 객체만 바꾸면 되서 깔끔하게 쓸 수 있다. 
- 스프링 컨테이너 올라가는 것들만 @Autowired가 적용된다.

## chap 05. 회원 관리 예제 - MVC 개발
- form 으로 값을 전달하는 경우 
- form 값을 받을 데이터 모델 생성 (memberForm)
- 요청되면 해당 컨트롤러의 parmas에 맵핑이 됨 
- 그 값을 가지고 멤버 생성
- "redirect:/" 하면 알아서 `/`로 리다이렉션 된다.
- 동적엔진을 통해 list 전달하면 알아서 처리할 수 있게 작동
- 현재 메모리에 저장하기때문에 껏다 키면 다 날라감는 문제.
- 그래서 데이터베이스를 사용해서 바꾸려고 한다.


## chap 06. H2 데이터 베이스 설치
- h2 설치
- JDBC 방식
- build.gradle 에 라이브러리 추가
- 테스트
  - @SpringBootTest
  - 를 넣으면 스프링을 아예 띄워서 실행한다.
  - @Transactional
  - 테스트케이스에 붙으면 자동으로 롤백해준다. 
  - 단위테스트: 순수하게 java code
  - 통합테스트: spring 부터 시작해서 테스트 하는 방식
  - JDBC Template
  - 반복적인 코드들을 제거해주는 라이브러리. 
  - JPA
  - sql 쿼리또한 ORM 맵핑으로 처리하는 방식
### REF
- https://sharonprogress.tistory.com/281
- https://u-it.tistory.com/300