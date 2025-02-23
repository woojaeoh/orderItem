package cafe_jw.starbucks.repository;

import cafe_jw.starbucks.domain.item.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){ // 수정(update
        if(item.getId() == null) {
            em.persist(item);
        }else{
            em.merge(item); //준영속 상태의 어떤 무언가를 영속성 컨텍스트로 재진입.
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
