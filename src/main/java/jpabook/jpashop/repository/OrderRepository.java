package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public Long save(Order order) {
        em.persist(order);
        return order.getId();
    }

    public Order findOne(Long orderId) {
        Order order = em.find(Order.class, orderId);
        return order;
    }

    /*
    public List<Order> findAll(OrderSearch orderSearch) {
        return em.createQuery("select o from Order o", Order.class).getResultList();
    }
    */


//
//    public List<Order> findByName(String name) {
//        return em.createQuery("select o from Order o where o.member.name = :name", Order.class)
//                .setParameter("name", name)
//                .getResultList();
//    }
//
//    public List<Order> findByState(OrderStatus orderStatus) {
//
//    }
}
