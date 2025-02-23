package cafe_jw.starbucks.controller;


import cafe_jw.starbucks.domain.item.Book;
import cafe_jw.starbucks.domain.item.Item;
import cafe_jw.starbucks.service.ItemService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
         model.addAttribute("form", new BookForm());
         return "items/createItemForm";
    }

    @PostMapping( "/items/new")
    public String create(BookForm form){

        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    //상품 수정
    @GetMapping("items/{itemId}/edit") // url의 {postId}를 PathVariable로 매핑시킨다. 여기서는 {itemId}와 "itemId"를 연결함.
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){ //경로 변수를 표시하기 위해 매개변수에 사용?
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());

        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());
        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    //수정 버튼을 클릭하게 되면 데이터 값은 준영속 엔티티가 된다. -> 더이상 영속성 컨텍스트가 관리하지 않는다.
    // -> 데이터 변경이 되지 않음 -> 해결책(1. 변경감지, 2. merge)
    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") Long itemId, @ModelAttribute("form") BookForm form){

        //컨트롤러에서 어설프게 엔티티 생성 X, 항상 변경감지를 사용해라.
//        Book book = new Book();
//        book.setId(book.getId());
//        book.setName(book.getName());
//        book.setPrice(book.getPrice());
//        book.setStockQuantity(book.getStockQuantity());
//        book.setAuthor(book.getAuthor());
//        book.setIsbn(book.getIsbn());

        //준영속 객체가 ID(식별자)로 영속성 컨텍스트를 조회한 후 , 조회한 공간에 값을 복사한다. 그 후 영속성 객체를 리턴
        // 여전히 준영속 객체는 남아있음.(Jpa가 관리X)
        //준영속 컨텍스트라는 개념은 없고 -> 단순히 관리되지 않는 객체가 메모리에 남아있는것임
        //더이상 할당할 수 있는 공간이 없으면 garbageCollector가 메모리 해제.
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());

        return "redirect:/items";
    }


}