package cafe_jw.starbucks.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name ="member_id")
    private Long id;

    private String name;

    @Embedded //내장 type을 포함했다는
    private Address address;

    @JsonIgnore// 주문 정보가 빠진다-> 순수하게 회원 정보만 나오게 할 수 있다.
    @OneToMany(mappedBy ="member") //연관관계의 주인은 N쪽이다.
    private List<Order> orders= new ArrayList<>();

}
