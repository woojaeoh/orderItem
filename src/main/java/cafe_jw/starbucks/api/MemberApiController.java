package cafe_jw.starbucks.api;

//요즘은 화면을 템플릿 엔진으로 만들기보단, single page application -> react, vue.js등으로 많이 개발
// 서버 개발자 입장에서는 예전처럼 서버에서 랜더링으로 내리는 것이 아니라 api통신을 통해
// api를 잘 설계하고 개발하는 것이 중요.
// api 패키지를 분리하는 이유 : 화면은 공통 에러 페이지가 나와야하고, api는 공통 에러 json이 나가야 함.

import cafe_jw.starbucks.domain.Member;
import cafe_jw.starbucks.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//@Controller + @ResponseBody  == Restcontroller
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members") //엔티티를 직접노출하면 안된다
    public List<Member> membersV1(){
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m-> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{ //api 스펙 중 노출하고 싶은 것만 노출할 수 있다. 이름 빼고 나머지는 다 감쌈.
        private String name;
    }



    @PostMapping ("/api/v1/members") //잘 안쓴다. ( Member api하나만으로 감당 x. api를 외부 노출 x, 엔티티 노출 x)
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    //실무에서는 절대 엔티티를 외부에 노출하면 안된다.  실무에서는 DTo객체를 사용해서 등록, 응답 받는거 사용해라
    @PostMapping("api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request){

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id); //커맨드와 쿼리의 분리?

        return new UpdateMemberResponse(findMember.getId(),findMember.getName());
    }

    @Data //별도의 DTO 사용.
    static class CreateMemberRequest{
        @NotEmpty
        private String name;
    }


    @Data
    static class CreateMemberResponse{ //등록된 id값 반환
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor //DTO에는 대충 라이브러리 막 써도 괜찮다.
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

}
