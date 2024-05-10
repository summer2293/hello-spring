package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRespository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    // 멤버 서비스에 있는 값은 new 다른 객체다.
    // new로 다른 객체가 생성되면 내용물이 달라질 수 있다.
    // 그래서 멤버 메모리 리포지토리랑, 테스트 멤버 메모리 리포지토리가 다를 수도 있다.
    // 같은걸로 테스트해야하는데, 다른 객체로 해야하는 문제가 있다. 이걸 해결하기 위해 컨스트럭터로 바꾼다.
    MemberService memberService;
    MemoryMemberRespository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRespository();
        memberService = new MemberService(memberRepository);
    }
    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }
    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("test1");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_예외() {
        Member member1 = new Member();
        member1.setName("test2");

        Member member2 = new Member();
        member2.setName("test2");

        // when
        memberService.join(member1);
        assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));
    }

}