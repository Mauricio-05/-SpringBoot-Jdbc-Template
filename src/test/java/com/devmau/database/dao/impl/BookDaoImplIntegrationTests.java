package com.devmau.database.dao.impl;

import com.devmau.database.TestDataUtil;
import com.devmau.database.dao.AuthorDao;
import com.devmau.database.domain.Author;
import com.devmau.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplIntegrationTests {

    private final AuthorDao authorDao;
    private final BookDaoImpl underTest;

    @Autowired
    public BookDaoImplIntegrationTests(BookDaoImpl underTest, AuthorDao authorDao){
        this.underTest = underTest;
        this.authorDao = authorDao;
    }


    @Test
    public void testThatBookCanBeCreatedAndRecalled(){

        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);

        Book book = TestDataUtil.createTestBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);

        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultiplesBookCanBeCreatedAndRecalled(){

        Author authorA = TestDataUtil.createTestAuthorA();
        authorDao.create(authorA);

        Author authorB = TestDataUtil.createTestAuthorB();
        authorDao.create(authorB);

        Author authorC = TestDataUtil.createTestAuthorC();
        authorDao.create(authorC);

        Book bookA = TestDataUtil.createTestBookA();
        bookA.setAuthorId(authorA.getId());
        underTest.create(bookA);

        Book bookB = TestDataUtil.createTestBookB();
        bookB.setAuthorId(authorB.getId());
        underTest.create(bookB);

        Book bookC = TestDataUtil.createTestBookC();
        bookC.setAuthorId(authorC.getId());
        underTest.create(bookC);

        List<Book> result = underTest.find();
        assertThat(result).hasSize(3).containsExactly(bookA, bookB, bookC);
    }

    @Test
    public void testThatBookCanBeUpdateAndRecalled(){

        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);

        Book book = TestDataUtil.createTestBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);

        book.setTitle("Update");
        underTest.update(book.getIsbn(),book);

        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void  testThatBookCanBeDeleted(){
        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);

        Book book = TestDataUtil.createTestBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);


        underTest.delete(book.getIsbn());

        Optional<Book> result  = underTest.findOne(book.getIsbn());

        assertThat(result).isEmpty();
    }

}
