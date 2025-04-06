package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemJpaRepositoryTest {

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Test
    void saveItem() {
        Book book = new Book();

        itemJpaRepository.save(book);

        Book item = (Book) itemJpaRepository.findById(book.getId()).orElse(null);

        assertEquals(book, item);
    }

    @Test
    void findByName() {
        Book book = new Book();
        book.setName("book1");

        itemJpaRepository.save(book);

        Book item = (Book) itemJpaRepository.findByName(book.getName()).orElse(null);

        assertEquals(book, item);
    }


    @Test
    void listOrder() {
        int size = itemJpaRepository.findAll().size();
        Book book1 = new Book();
        itemJpaRepository.save(book1);

        Book book2 = new Book();
        itemJpaRepository.save(book2);

        List<Book> list = itemJpaRepository.findAll()
                .stream()
                .filter(i -> i instanceof Book)
                .map(i -> (Book) i)
                .collect(Collectors.toList());

        assertEquals(2, list.size()-size);
    }

    @Test
    void paging() {
        List<Book> books = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Book book = new Book();
            book.setName("book" + i);
            books.add(book);
        }

        itemJpaRepository.saveAll(books);

        int pageSize = 10;

        for (int i = 0; i < 3 ; i++) {
            PageRequest pageRequest = PageRequest.of(i, pageSize, Sort.by(Sort.Direction.DESC, "id"));
            List<Book> list = itemJpaRepository.findAll(pageRequest)
                    .stream()
                    .filter(item -> item instanceof Book)
                    .map(item -> (Book) item)
                    .collect(Collectors.toList());

            System.out.println("===============");
            for (Book book : list) {
                System.out.println("book.getName() = " + book.getName());
            }
        }
    }



}