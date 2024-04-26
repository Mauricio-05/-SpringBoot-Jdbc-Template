package com.devmau.database.dao.impl;

import com.devmau.database.TestDataUtil;
import com.devmau.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;


import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void  testThatCreateAuthorGeneratesCorrectSql(){
        Author author = TestDataUtil.createTestAuthorA();
        underTest.create(author);

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id,name,age) VALUES (?,?,?)"),
                eq(1L), eq("Abigail Rose"), eq(80)
        );
    }

    @Test
    public void testThatFindOneAuthorGeneratesCorrectSql(){
        underTest.findOne(1L);

        verify(jdbcTemplate).query(
                eq("SELECT id,name,age FROM authors WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
                eq(1L)
        );
    }

    @Test
    public void TestThatFindManyAuthorGeneratesCorrectSql(){
        underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT id,name,age FROM authors"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()
        );
    }

    @Test
    public void testThatUpdateGeneratesCorrectSql(){
        Author author =  TestDataUtil.createTestAuthorA();
        underTest.update(author.getId(), author);

        verify(jdbcTemplate).update(
                eq("UPDATE authors SET name = ?, age = ? WHERE id = ?"),
                eq("Abigail Rose"),
                eq(80),
                eq(1L)
        );
    }

    @Test
    public void testThatDeleteGeneratesCorrectSql(){
        underTest.delete(1L);
        verify(jdbcTemplate).update(
                eq("DELETE FROM authors WHERE id = ?"),
                eq(1L)
        );
    }
}
