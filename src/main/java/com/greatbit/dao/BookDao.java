package com.greatbit.dao;

import com.greatbit.models.Book;

import java.util.List;

public interface BookDao {
    List<Book> findAll();

    Book save(Book book);

    Book getById(String bookID);

    Book update(Book book);

    void delete(Book book);

    void deleteAll();
}
