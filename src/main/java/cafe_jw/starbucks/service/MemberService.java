package cafe_jw.starbucks.service;

import cafe_jw.starbucks.domain.Member;
import cafe_jw.starbucks.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly= true)
@RequiredArgsConstructor //lombok기능이면서 final 필드에 대한 생성자 자동 주입
public class MemberService { //서비스 계층 -> 내가 설계한 엔티티의 비즈니스 로직단. ex) 회원명이 같은 사람을 회원가입 시킬 수 없다.-> 중복 회원 검증 메서드

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId(); //test를 위해 id를 리턴한다.
    }

    //중복 회원 검증 메서드
    public void validateDuplicateMember(Member member) {
        //예외 처리
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //회원 한명 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional //회원 이름 수정 -> 변경감지 이용.
    public void update(Long id , String name) {
        Member member = memberRepository.findOne(id); //영속성 컨텍스트에서 조회
        member.setName(name);//변경감지 실행
    }

}
