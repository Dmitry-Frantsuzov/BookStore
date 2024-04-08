CREATE TABLE IF NOT EXISTS book(
    book_id serial primary key,
    name varchar(255),
    author varchar(255),
    pages int
);