package cafe_jw.starbucks.service;


import cafe_jw.starbucks.domain.item.Item;
import cafe_jw.starbucks.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly =true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional //커밋 시(트랜잭션 종료 시점) flush가 변경점을 찾는다.(dirty checking) 이후 영속성 컨텍스트에 update 쿼리를 날린다.
                    // (변경감지 사용해라)
    public void updateItem(Long itemId, String name ,int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    //상품 모두 조회
    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    //상품 하나 조회
    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }


}
