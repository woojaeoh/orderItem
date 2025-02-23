package cafe_jw.starbucks.domain.item;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("O")
public class Outer extends Item{

    private String sizegram;
}
