package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemoryMemberRepository memberRepository;

    @Test
    void 회원가입() {
        // 한번 만들면 값을 데이터베이스에 넣기 때문에 테스트가 어려움
        // rollback 처리를 추가해서 검증할 수 있다.
        // Transactional 어노테이션을 테스트케이스에 넣으면 반영이 안되고 다 지워진다.
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