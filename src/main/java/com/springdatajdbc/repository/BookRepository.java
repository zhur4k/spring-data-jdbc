package com.springdatajdbc.repository;

import com.springdatajdbc.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository{
    void save(Book book);

    void update(Book book);

    void deleteById(Long id);

    Optional<Book> findById(Long id);

    List<Book> findAll();
}
