package cafe_jw.starbucks.domain;


import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable //임베디드 타입-> 값 타입을 여러개 래핑해서 의미있는 이름으로 새롭게 만듦.
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address(){
    }


    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
