package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRespository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(),  member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
//        Optional.ofNullable() 로 감싸면 클라이언트에서 어떻게 할 수 있다.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals((name)))
                .findAny(); // optional 로 감싸서 반환된다.
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
