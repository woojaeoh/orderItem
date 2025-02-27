package cafe_jw.starbucks.service;

import cafe_jw.starbucks.domain.*;
import cafe_jw.starbucks.domain.item.Item;
import cafe_jw.starbucks.repository.ItemRepository;
import cafe_jw.starbucks.repository.MemberRepository;
import cafe_jw.starbucks.repository.OrderRepository;
import cafe_jw.starbucks.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    //==주문==//
    @Transactional
    public Long order (Long memberId, Long itemId, int count){

        //엔티티 조회 -> 회원 ,상품 생성
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
//        delivery.setStatus(DeliveryStatus.READY);

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(),count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장 //하나만 저장해줘도 cascade로 연관된 delivery와 orderItem이 자동으로 저장된다.
        orderRepository.save(order);

        return order.getId();
    }

    //주문 취소
    @Transactional
    public void cancelOrder(Long orderId){

        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        //주문 취소
        order.cancel();
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }


}
