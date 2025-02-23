package cafe_jw.starbucks.domain.item;

import cafe_jw.starbucks.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //단일 테이블에 하위 항목들 모두 작성
@DiscriminatorColumn(name="dtype")
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity; //재고 필드가 있는 곳에서 연관관계 메서드가 작성되는게 좋다.

    //비즈니스 로직
    //stock 수량 증가 로직
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    //stock 감소 로직
    public void removeStock(int quantity){
        int restStock = stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }

        this.stockQuantity = restStock;
    }
}