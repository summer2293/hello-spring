package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// 스프링이 컨테이너 어노테이션을 걸면
// 스프링이 실행할때 객체를 생성해서 가지고있다.
// 이 컨테이너를 빈이라고 함
@Controller
public class MemberContoller {
    private final MemberService memberService;
    // new 해서 쓰면 멤버 서비스들을 주문이나 다른곳에서 쓸 수 있는데,
    // 여기에 new 해버리면 하나만 생성해서 공용으로 사용하는게 좋을 경우 생성자 연결 해주면 됨
    // auto wired를 하면 스프링이 자동으로 멤버 컨트롤러의 멤버서비스와 연결을 시켜준다.
    // 이렇게 하려면 해당 서비스도 @Service 어노테이션을 걸어야한다
    // 이러면 스프링에 하나의 빈으로 등록됨, @Repository 도 있다.
    // 스프링 주입하는방법
    // 컴포넌트 스캔 -> @ 를 사용해 주입하는 방법
    // 내부에 컴포넌트라는 어노테이션이 있다.
    // 컴포넌트 어노테이션을 스캔하면 스프링이 알아서 읽어 띄운다.
    // 아무데나 컴포넌트 스캔을 붙혀도 되는지? <- 기본은 안된다. 어디서부터 되냐면
    // HelloSpringApplication 패키지 하위에 있는 값은 다 된다.
    // 자바 코드로 직접 스프링 빈 등록하는 방법
    @Autowired // 연결
    public MemberContoller(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        System.out.println("member:"+member.getName());
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String members(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "memberList";
    }

}
