package com.springdatajdbc.repository.impl;

import com.springdatajdbc.model.Book;
import com.springdatajdbc.repository.BookRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

public class BookRepositoryImpl implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPublicationYear(rs.getDate("publication_year").toLocalDate());
        return book;
    };

    @Override
    public void save(Book book) {
        String sql = "INSERT INTO Book (title, author, publication_year) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublicationYear());
    }

    @Override
    public void update(Book book) {
        String sql = "UPDATE Book SET title = ?, author = ?, publication_year = ? WHERE id = ?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublicationYear());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Book WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM Book WHERE id = ?";
        return jdbcTemplate.query(sql, bookRowMapper, id).stream().findFirst();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM Book";
        return jdbcTemplate.query(sql, bookRowMapper);
    }
}
