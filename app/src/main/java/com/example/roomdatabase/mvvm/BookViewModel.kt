package com.example.roomdatabase.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BookViewModel(application: Application): AndroidViewModel(application) {
    val books: LiveData<List<Book>>

    private  val repositorty : BookRepository

    init {
        val bookDao = BookDatabase.getDatebase(application).bookDao()
        repositorty = BookRepository(bookDao)
        books = repositorty.allBooks


    }

    fun addBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            repositorty.insert(book)
        }
    }
    fun updateBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            repositorty.update(book)
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            repositorty.delete(book)
        }
    }

    fun deleteAllBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            repositorty.deleteAll()
        }
    }


}