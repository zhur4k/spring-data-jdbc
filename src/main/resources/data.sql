CREATE TABLE IF NOT EXISTS Book(
    id BIGINT PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    publication_year DATE
);

INSERT INTO BOOK (id, title, author, publication_year)
VALUES (1, 'Effective Java', 'Joshua Bloch', '2008-05-28');