package com.greatbit.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BooksStorage {
    private static List<Book> books = new ArrayList<>();

    static {
        books.add(new Book(
                UUID.randomUUID().toString(),
                "Учение Дона Хуана",
                "Карлос Кастанеда",
                400));
        books.add(new Book(
                UUID.randomUUID().toString(),
                "Богатый папа, Бедный папа",
                "Роберт Киосаки",
                300));
    }

    public static List<Book> getBooks() {
        return books;
    }


}
