package jpabook.jpashop.repository.querydsl;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberQueryDslTest {
    
    @Autowired
    MemberQueryDsl memberQueryDsl;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void findAllMember() {
        List<Member> members = memberQueryDsl.findAll();
        members.stream().forEach(m -> System.out.println("m = " + m.getName()));
    }
    
    @Test
    void findMember() {
        Member find = memberQueryDsl.findOne(1L);
        Member savedMember = memberRepository.findOne(find.getId());

        assertThat(find).isEqualTo(savedMember);
    }

    @Test
    void findByMemberName() {
        Member member = new Member();
        member.setName("userA");

        memberRepository.save(member);

        List<Member> membersuserA = memberQueryDsl.findByName("userA");

        assertThat(2).isEqualTo(membersuserA.size());
    }


}