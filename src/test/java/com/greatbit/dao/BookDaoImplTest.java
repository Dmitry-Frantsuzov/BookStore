package com.greatbit.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.greatbit.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(
        properties = {"jdbcUrl=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1"}
)

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookDaoImplTest {

    @Autowired
    private BookDao bookDao;


    //еред каждым запуском теста удали базу данных
    @BeforeEach
    public void beforeEach() {
        bookDao.deleteAll();
    }


    @Test
    public void contextCreated() {

    }

    @Test
    public void saveSavesDataToDbAndReturnsEntityWithId() {
        Book book = bookDao.save(new Book("book name", "book author", 1));
        assertThat(book.getId()).isNotBlank();
        assertThat(bookDao.findAll()).extracting("id").containsExactly(book.getId());
    }

    @Test
    void deleteAllDeletesAllDao() {
        bookDao.save(new Book("book name", "book author", 1));
        assertThat(bookDao.findAll()).isNotEmpty();
        bookDao.deleteAll();
        assertThat(bookDao.findAll().isEmpty());
    }


    @Test
    void findAllReturnsAllBooks() {
        assertThat(bookDao.findAll()).isEmpty();
        bookDao.save(new Book("book name", "book author", 1));
        assertThat(bookDao.findAll()).isNotEmpty();
    }


    @Test
    void getByIdThrowsRuntimeExceptionIfNoBookFound() {
        assertThatThrownBy(() -> bookDao.getById("1")).isInstanceOf(RuntimeException.class);
    }


    @Test
    void getByIdReturnsCorrectBook() {
        Book book = bookDao.save(new Book("book name", "book author", 1));
        Book otherBook = bookDao.save(new Book("other book name 2", "other book author 2", 2));

        assertThat(bookDao.getById(book.getId())).isNotNull()
                .extracting("name")
                .isEqualTo(book.getName());
    }


    @Test
    void updateUpdatesDataInDb() {
        Book book = bookDao.save(new Book("book name", "book author", 1));
        book.setName("new name");

        bookDao.update(book);
        assertThat(bookDao.getById(book.getId()).getName()).isEqualTo("new name");
    }

    @Test
    void updateThrowsExceptionOnUpdatingNotSavedBook() {
        assertThatThrownBy(() -> bookDao.update(new Book("book name", "book author", 1)))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteDeletesCorrectDate() {
        Book bookToKeep = bookDao.save(new Book("book name", "book author", 1));
        Book bookToDelete = bookDao.save(new Book("other book name 2", "other book author 2", 2));

        bookDao.delete(bookToDelete);
        assertThat(bookDao.getById(bookToKeep.getId())).isNotNull();
        assertThatThrownBy(() -> bookDao.getById(bookToDelete.getId())).isInstanceOf(RuntimeException.class);
    }

}