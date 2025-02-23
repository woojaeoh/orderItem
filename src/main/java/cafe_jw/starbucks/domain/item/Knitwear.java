package cafe_jw.starbucks.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("K")
@Getter @Setter
public class Knitwear extends Item {

    private String material;

}
