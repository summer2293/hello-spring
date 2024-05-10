package hello.hellospring.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    // 웹 브라우저에서 /hello 로 던지면 스프링 부트는 톰캣이란 웹서버를 내장해서,
    // @GetMapping("hello") 인 값을 찾는다.
    // model data 값에 hello를 넣고
    // return에서 "hello" 를 하는데, 이 hello는 resources/templates에서 파일을 찾는다.
    // <p th:text="'안녕하세요. ' + ${data}" >안녕하세요. 손님</p>
    // 여기 값에 {data} 에 템플릿 엔진이 값을 넣어준다.
    // view resolver
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!");
        return "hello";
    }

    // request 가 오면 톰캣서버가 컨테이너를 거침
    // return 값을 뷰 리졸버가 받아 템플릿을 찾음
    // 템플릿 엔진이 렌더링해서 html 을 반환
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name", required = false, defaultValue = "meow") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody // 응답 값을 직접 넣어주겠다.
    public String helloString(@RequestParam(value = "name", required = false, defaultValue = "meow") String name, Model model) {
        return "hello" + name; // 이러면 string 값이 그대로 내려간다.
    }

    // 객체로 내리고 싶을 경우
    @GetMapping("hello-api")
    @ResponseBody // 응답 값을 직접 넣어주겠다.
    public Hello helloApi(@RequestParam(value = "name", required = false, defaultValue = "meow") String name, Model model) {
        // response body 를 사용하면 톰캣 내장 서버에서 컨테이너에 던짐
        // responsebody annotation 이 붙어있지 않으면, view resolver 에게 던지는데
        // responsebody 가 있음 httpmessage converter 가 동작한다.
        // 객체면 jsonconverter, 문자면 stringconverter이 동작한다.
        // http 에 응답을 던져야 겠다고 넘김.
        // 객체를 줄 경우에는 스프링 입장에서는 json 방식으로 만들어서 기본으로 http 응답에 반환하는게 기본 정책이다.
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }
    public class Hello {
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
