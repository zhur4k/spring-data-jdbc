package com.springdatajdbc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springdatajdbc.model.Book;
import com.springdatajdbc.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllBooksSuccess() throws Exception {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book Title");
        book1.setAuthor("Author");
        book1.setPublicationYear(LocalDate.now());

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book Title");
        book2.setAuthor("Author");
        book2.setPublicationYear(LocalDate.now());

        List<Book> books = Arrays.asList(book1, book2);

        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(books)));
    }

    @Test
    void getAllBooksException() throws Exception {
        when(bookService.getAllBooks()).thenThrow(RuntimeException.class);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void createBookSuccess() throws Exception {
        Book book = new Book();
        book.setTitle("Book Title");
        book.setAuthor("Author");
        book.setPublicationYear(LocalDate.now());

        mockMvc.perform(post("/api/books/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());

        verify(bookService, times(1)).saveBook(any(Book.class));
    }

    @Test
    void createBookException() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        book.setAuthor("Author");
        book.setPublicationYear(LocalDate.now());

        doThrow(RuntimeException.class).when(bookService).saveBook(any(Book.class));

        mockMvc.perform(post("/api/books/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isInternalServerError());

        verify(bookService, times(1)).saveBook(any(Book.class));
    }

    @Test
    void updateBookSuccess() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        book.setAuthor("Author");
        book.setPublicationYear(LocalDate.now());

        mockMvc.perform(put("/api/books/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());

        verify(bookService, times(1)).updateBook(any(Book.class));
    }

    @Test
    void updateBookException() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        book.setAuthor("Author");
        book.setPublicationYear(LocalDate.now());

        doThrow(RuntimeException.class).when(bookService).updateBook(any(Book.class));

        mockMvc.perform(put("/api/books/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isInternalServerError());

        verify(bookService, times(1)).updateBook(any(Book.class));
    }

    @Test
    void deleteBookSuccess() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/books/delete/{id}", id))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteBook(id);
    }

    @Test
    void deleteBookException() throws Exception {
        Long id = 1L;

        doThrow(RuntimeException.class).when(bookService).deleteBook(id);

        mockMvc.perform(delete("/api/books/delete/{id}", id))
                .andExpect(status().isInternalServerError());

        verify(bookService, times(1)).deleteBook(id);
    }

    @Test
    void getBookByIdSuccess() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        book.setAuthor("Author");
        book.setPublicationYear(LocalDate.now());

        when(bookService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/api/books/{id}", book.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(book)));

        verify(bookService, times(1)).getBookById(book.getId());
    }

    @Test
    void getBookByIdException() throws Exception {
        Long id = 1L;

        when(bookService.getBookById(1L)).thenThrow(RuntimeException.class);

        mockMvc.perform(get("/api/books/{id}", id))
                .andExpect(status().isInternalServerError());

        verify(bookService, times(1)).getBookById(id);
    }
}