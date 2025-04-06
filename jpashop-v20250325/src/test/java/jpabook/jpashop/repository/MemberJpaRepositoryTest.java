package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    void saveJpa() {
        Member member = new Member();
        member.setName("rok");

        memberJpaRepository.save(member);

        Member find = memberJpaRepository.findById(member.getId()).orElse(null);

        assertThat(member).isEqualTo(find);
    }

    @Test
    void findByName() {
        Member member = new Member();
        member.setName("rok");

        memberJpaRepository.save(member);

        Member find = memberJpaRepository.findByName(member.getName()).orElse(null);

        assertThat(member).isEqualTo(find);
    }

    @Test
    void findAllJpa() {
        int size = memberJpaRepository.findAll().size();

        Member member1 = new Member();
        member1.setName("name1");
        Member member2 = new Member();
        member2.setName("name2");

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        List<Member> list = memberJpaRepository.findAll();

        //같은 엔티티를 사용하면 다른repository를 사용하더라도 같은table에저장됨.
        //기존 DB데이터가 2개존재
        assertThat(2).isEqualTo(list.size() - size);
    }

    @Test
    void paging() {
        List<Member> members = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Member member = new Member();
            member.setName("member" + i);
            members.add(member);
        }

        memberJpaRepository.saveAll(members);

        int pageSize = 10;

        for (int i = 0; i < 3 ; i++) {
            PageRequest pageRequest = PageRequest.of(i, pageSize, Sort.by(Sort.Direction.DESC, "id"));
            List<Member> list = memberJpaRepository.findAll(pageRequest).stream().toList();
            System.out.println("===============");
            for (Member member : list) {
                System.out.println("member.getName() = " + member.getName());
            }
        }
    }

}