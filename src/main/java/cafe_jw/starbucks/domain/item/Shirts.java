package cafe_jw.starbucks.domain.item;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("S")
public class Shirts extends Item{

    private String fabric;
    private String care;
}
