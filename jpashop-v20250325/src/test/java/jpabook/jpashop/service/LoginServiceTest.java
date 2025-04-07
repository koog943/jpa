package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.querydsl.MemberQueryDsl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LoginServiceTest {

    @Autowired
    LoginService loginService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void login() throws IllegalAccessException {
        Member member = new Member();
        member.setName("rok");
        member.setPassword("qwe123");

        memberRepository.save(member);
        Member byLoginId = loginService.login(member.getName(), member.getPassword());

        assertThrows(IllegalAccessException.class, () -> loginService.login("123", "123"));
        assertEquals(member, byLoginId);
    }
}