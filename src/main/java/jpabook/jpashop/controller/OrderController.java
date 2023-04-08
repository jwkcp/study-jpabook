package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    private void createDummyData() {
        Member member1 = new Member();
        member1.setName("홍길동");
        member1.setAddress(new Address("서울", "테헤란로1", "11111"));

        Member member2 = new Member();
        member2.setName("홍길동");
        member2.setAddress(new Address("서울", "테헤란로1", "11111"));

        memberService.join(member1);
        memberService.join(member2);

        Item item1 = new Book();
        item1.setName("책1");
        item1.setPrice(1000);
        item1.setStockQuantity(100);

        Item item1 = new Book();
        item1.setName("책1");
        item1.setPrice(1000);
        item1.setStockQuantity(100);
    }

    // 주문폼 보여주기
    @GetMapping("order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        createDummyData();

        return "order/orderForm.html";
    }

    // 주문 생성
    @PostMapping("order")
//    public String create(@Valid OrderForm orderForm, BindingResult bindingResult) {
    public String create(@RequestParam Long memberId, @RequestParam Long itemId, @RequestParam int count) {
//        Member member = memberService.findOne(memberId);
//        Item item = itemService.findOne(itemId);

//        Delivery delivery = new Delivery();
//        delivery.setAddress(member.getAddress());
//        delivery.setStatus(DeliveryStatus.READY);
//
//        OrderItem orderItem = OrderItem.createOrderItem(
//                item,
//                item.getPrice(),
//                count
//        );
//
//        Order order = Order.createOrder(
//                member,
//                delivery,
//                orderItem
//        );

        orderService.order(memberId, itemId, count);

        return "order/orderList.html";
    }
    // 주문 수정
    // 주문 목록
    // 주문 취소
}
