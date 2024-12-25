package com.springdatajdbc.service;

import com.springdatajdbc.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book getBookById(Long id);

    void saveBook(Book book);

    void updateBook(Book book);

    void deleteBook(Long id);
}
