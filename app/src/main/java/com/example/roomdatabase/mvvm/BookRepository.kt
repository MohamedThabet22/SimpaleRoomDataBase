package com.example.roomdatabase.mvvm

import androidx.lifecycle.LiveData


class BookRepository(private val bookDao: BookDoa) {
    val allBooks: LiveData<List<Book>> = bookDao.getAllBooks()

    suspend fun insert(book: Book) {
        bookDao.insertBook(book)
    }

    suspend fun update(book: Book) {
        bookDao.updateBook(book)
    }

    suspend fun delete(book: Book) {
        bookDao.deleteBook(book)
    }

    suspend fun deleteAll() {
        bookDao.deleteAllBooks()
    }
}

