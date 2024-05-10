package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
