package com.example.demo.repository.impl;

import com.example.demo.db.BookDB;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {
    @Override
    public List<Book> findAll() {
        return BookDB.books;
    }
}
