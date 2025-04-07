package jpabook.jpashop.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.QMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberQueryDsl {

    @Autowired
    JPAQueryFactory query;
    QMember qMember = new QMember("m");

    public Member findOne(Long id) {
        return query.selectFrom(qMember)
                .where(qMember.id.eq(id))
                .fetchOne();
    }

    public List<Member> findAll() {
        return query.selectFrom(qMember).fetch();
    }

    public List<Member> findByName(String name) {
        return query.selectFrom(qMember)
                .where(qMember.name.eq(name))
                .fetch();
    }

    public Optional<Member> findByLoginId(String name, String password) {
        Member member = query.selectFrom(qMember)
                .where(qMember.name.eq(name))
                .where(qMember.password.eq(password))
                .fetchOne();


        return Optional.ofNullable(member);
    }

}
