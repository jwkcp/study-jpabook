package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.fail;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * 상품주문
     */
    @Test
    void order() {
        Member member = createMember("회원1", new Address("서울", "강가", "123-123"));

        Item book = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);

        // 주문 상태 체크
        Assertions.assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        // 주문 수량 체크
        Assertions.assertEquals(1, getOrder.getOrderItems().size());
        // 주문 가격
        Assertions.assertEquals(10000 * 2, getOrder.getTotalPrice());
        // 주문 후 재고 체크
        Assertions.assertEquals(8, book.getStockQuantity());
    }

    @Test
    void exceedStockQuantity() {
        Member member = createMember("회원1", new Address("서울", "강가", "123-123"));
        Item book = createBook("시골 JPA", 10000, 10);

        int count = 100;

        Assertions.assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), book.getId(), count));
//        fail("재고 수량 부족 예외가 발생해야 합니다");
    }

    /**
     * 주문취소
     */
    @Test
    void cancelOrder() {
        Member member = createMember("홍길동", new Address("서울", "강가", "123"));
        Item book = createBook("시골JPA", 10000, 10);
    }

    /**
     * 재고수량초과
     */
    @Test
    void exceedOrderQuantity() {

    }

    private Item createBook(String name, int price, int stockQuantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }
}