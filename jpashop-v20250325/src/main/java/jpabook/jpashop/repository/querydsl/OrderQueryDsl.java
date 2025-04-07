package jpabook.jpashop.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.QItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderQueryDsl {

    @Autowired
    JPAQueryFactory query;
    QOrder qOrder = new QOrder("o");
    QMember qMember = new QMember("m");
    QDelivery qDelivery = new QDelivery("d");
    QOrderItem qOrderItem = new QOrderItem("io");
    QItem qItem = new QItem("i");


    public Order findOne(Long id) {
        return query.selectFrom(qOrder)
                .where(qOrder.id.eq(id))
                .fetchOne();
    }

    public List<Order> findAllString(OrderSearch orderSearch) {
        return query.selectFrom(qOrder)
                .join(qOrder.member)
                .where(qOrder.status.eq(orderSearch.getOrderStatus())
                        .and(qOrder.member.name.eq(orderSearch.getMemberName())))
                .fetch();
    }

    public List<Order> findAllWithItem() {
        return query.selectFrom(qOrder)
                .distinct()
                .join(qOrder.member, qMember).fetchJoin()
                .join(qOrder.delivery).fetchJoin()
                .join(qOrder.orderItems).fetchJoin()
                .join(qOrderItem.item, qItem).fetchJoin()
                .fetch();
    }

    public List<Order> findAllWithMemberDelivery() {
        return query.selectFrom(qOrder)
                .join(qOrder.member, qMember).fetchJoin()
                .join(qOrder.delivery, qDelivery).fetchJoin()
                .fetch();
    }

    public List<Order> findAllWithMemberDelivery(int offset, int limit) {
        return query.selectFrom(qOrder)
                .join(qOrder.member, qMember).fetchJoin()
                .join(qOrder.delivery, qDelivery).fetchJoin()
                .offset(offset)
                .limit(limit)
                .orderBy(qOrder.id.desc())
                .fetch();
    }


}
