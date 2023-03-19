package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
//    @Rollback(value = false)
    public void join() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);
        
        // then
        Member findMember = memberService.findOne(savedId);

        Assertions.assertEquals(member, findMember);
    }

    @Test(expected) // TODO: JUnit5 문법으로 바꿔야 함
    public void duplicatedMember() throws Exception {
        Member member1 = new Member();
        Member member2 = new Member();

        member1.setName("kim1");
        member2.setName("kim1");


        try {
            Long savedMember1 = memberService.join(member1);
            Long savedMember2 = memberService.join(member2);
        } catch (IllegalStateException e) {
            return;
        }

        Assertions.fail("예외가 발생해야 합니다.");
    }
}