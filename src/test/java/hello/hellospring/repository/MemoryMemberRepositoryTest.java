package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 테스트의 순서는 보장이 안된다.
    // 따라서 다른 객체가 저장이 되어 다른 객체가 나와버렸다.
    // 따라서 테스트가 끝나면 깔끔하게 테스트를 종료시켜야한다.
    // 테스트를 만들고 검증을 하는 방법 -> TDD 테스트 주도 개발
    @AfterEach
    public void afterEach() {
        // member repository 에 clearstore 생성
        // 따라서 테스트가 끝날때마다 clearStore가 되므로 테스트에 문제가 없어진다.
        repository.clearStore();
    }
    @Test
    public void save() {
        Member member = new Member();

        member.setName("test1");
        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        assert member == result;
        // 궁금
        assertThat(member).isEqualTo(result);

    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("test1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("test2");
        repository.save(member2);

        Member result = repository.findByName(member1.getName()).get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("test1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("test2");
        repository.save(member2);

        List<Member> members = repository.findAll();
        assertThat(members.size()).isEqualTo(2);
    }
}
