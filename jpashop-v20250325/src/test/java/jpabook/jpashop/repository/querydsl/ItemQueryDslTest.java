package jpabook.jpashop.repository.querydsl;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemQueryDslTest {

    @Autowired
    ItemQueryDsl itemQueryDsl;

    @Autowired
    ItemRepository itemRepository;

    @Test
    void findItem() {
        Book book = new Book();
        book.setName("name1");

        itemRepository.save(book);

        Book find = (Book)itemQueryDsl.findOne(book.getId());

        assertEquals(book, find);
    }

    @Test
    void findAll() {
        int size = itemRepository.findAll().size();

        Book book1 = new Book();
        book1.setName("book1");
        Book book2 = new Book();
        book2.setName("book2");

        itemRepository.save(book1);
        itemRepository.save(book2);

        List<Item> items = itemQueryDsl.findAll();

        assertEquals(2, items.size() - size);
    }

}