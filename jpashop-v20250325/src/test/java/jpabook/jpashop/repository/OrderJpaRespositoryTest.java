package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderJpaRespositoryTest {

    @Autowired
    OrderJpaRespository orderJpaRespository;

    @Test
    void saveOrder() {
        Order order = new Order();
        orderJpaRespository.save(order);

        Order findOrder = orderJpaRespository.findById(order.getId()).orElse(null);

        assertEquals(order, findOrder);
    }

    @Test
    void listOrder() {
        int size = orderJpaRespository.findAll().size();
        Order order1 = new Order();
        orderJpaRespository.save(order1);

        Order order2 = new Order();
        orderJpaRespository.save(order2);

        List<Order> list = orderJpaRespository.findAll();

        assertEquals(2, list.size()-size);
    }

    @Test
    void paging() {
        List<Order> orders = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Order order = new Order();
            orders.add(order);
        }

        orderJpaRespository.saveAll(orders);

        int pageSize = 10;

        for (int i = 0; i < 3 ; i++) {
            PageRequest pageRequest = PageRequest.of(i, pageSize, Sort.by(Sort.Direction.DESC, "id"));
            List<Order> list = orderJpaRespository.findAll(pageRequest).stream().toList();
            System.out.println("===============");
            for (Order order : list) {
                System.out.println("order.getId() = " + order.getId());
            }
        }
    }

}