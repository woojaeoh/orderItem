package cafe_jw.starbucks.repository;

import cafe_jw.starbucks.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext //spring boot가 entity manager를 주입해줌.
    private final EntityManager em;

    //저장
    public void save(Member member) {
        em.persist(member); //영속성 컨텍스트에 저장.
      //  return member.getId(); // 저장 후에는 가급적이면 아이디 정도만 조회( 전체를 return X) id를 기반해서 검증하기 쉽다.
    }

    //멤버 한명 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    //모두 조회
    public List<Member> findAll(){ //jpa는 자바 코드로 쿼리를 작성할 수 있다.
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 파라미터 바인딩에 의해 특정 이름을 가진 사람 출력(검색 기능)
    public List<Member> findByName(String name){ //jpa에선 메소드로 쿼리를 작성할 수 있음.
        return em.createQuery("select m from Member m where m.name = :name", Member.class) //(쿼리, 반환타입) /JPQL-> jpa에서 제공하는 SQL쿼리
                .setParameter("name",name)
                .getResultList();
    }
}
