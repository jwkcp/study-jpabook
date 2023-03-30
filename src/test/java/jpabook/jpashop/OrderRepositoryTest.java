package jpabook.jpashop;

import jakarta.transaction.Transactional;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;
    
    @Test
    void findAll() {
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName("홍길동");
        orderSearch.setOrderStatus(OrderStatus.ORDER);
        List<Order> orders = orderRepository.findAll(orderSearch);

        System.out.println("====================");
        System.out.println(orders);
        System.out.println("====================");
    }
}
