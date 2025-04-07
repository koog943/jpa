package jpabook.jpashop.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.QItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemQueryDsl {

    @Autowired
    JPAQueryFactory query;

    QItem qItem = new QItem("i");

    public Item findOne(Long id) {
        return query.selectFrom(qItem)
                .where(qItem.id.eq(id))
                .fetchOne();
    }

    public List<Item> findAll() {
        return query.selectFrom(qItem)
                .fetch();
    }


}
