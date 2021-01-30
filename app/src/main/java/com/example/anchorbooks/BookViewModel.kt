package com.example.anchorbooks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.anchorbooks.model.BookDetails
import com.example.anchorbooks.model.BookItem
import com.example.anchorbooks.model.local.BookDataBase

class BookViewModel(application: Application): AndroidViewModel(application) {

    private val myRepository: BookRepository
    val allBooks: LiveData<List<BookItem>>
    val bookSelection = MutableLiveData<Int>()

    init{
        val myDao = BookDataBase.getDatabase(application).bookDao()
        myRepository = BookRepository(myDao)
        allBooks = myRepository.bookList
        myRepository.getBooksFromApi()
    }

    fun getOneBookDetails(id: Int): LiveData<BookDetails>{
        myRepository.getBooksDetailsFromApi(id)   //consulta a inet
        return myRepository.getBookDetails(id)   //consulta a base
    }

    fun bookSelected (bookId: Int){
        bookSelection.value = bookId
    }

}