package toyproject.memories.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.memories.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager entityManager;

    public Member saveMember(Member member){
        entityManager.persist(member);
        return member;
    }

    public Optional<Member> findByName(String name){
        List<Member> member = entityManager.createQuery("select m from Member as m where m.name = :name", Member.class)
                .setParameter("name",name)
                .getResultList();
        return member.stream().findAny();
    }
}
