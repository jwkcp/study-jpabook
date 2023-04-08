package jpabook.jpashop.controller;


import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderForm {
    private Long id;

//    @NotEmpty(message = "주문자 정보는 필수입니다.")
    private Member member;
    private List<OrderItem> orderItems;

    private int count;
}
