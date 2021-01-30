package com.example.anchorbooks

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.anchorbooks.model.BookDetails
import com.example.anchorbooks.model.BookRetrofitClient
import com.example.anchorbooks.model.local.BookDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookRepository(private val myBookDao: BookDao) {

    private val myRetrofit = BookRetrofitClient.retrofitInstance()

    /*
     * Esta variable contiene el listado de libros que están guardados en local.
     */
    val bookList = myBookDao.getAllBookItems()

    /*
     * Esta función retorna los detalles de un libro guardado en local, dado un 'id'.
     */
    fun getBookDetails(id: Int): LiveData<BookDetails> {
        return myBookDao.getOneBookDetails(id)
    }

    /*
     * Utilizando retrofit, va a buscar la lista de libros de la Api y los guarda en local.
     */
    fun getBooksFromApi() = CoroutineScope(Dispatchers.IO).launch{

        val service = kotlin.runCatching { myRetrofit.fetchAllBooks() }
        service.onSuccess {
            when(it.code()) {

                in 200..299 -> it.body()?.let { list ->
                    myBookDao.insertAllBookItems(list)
                }

                in 300..599 -> Log.e("ERROR", it.errorBody().toString())
                else -> Log.d("ERROR", it.errorBody().toString())

            }
        }

        service.onFailure {

            Log.e("ERROR", it.message.toString())

        }
    }

    /*
     * Utilizando retrofit, va a buscar los detalles de un libro dado un 'id' y los guarda en local.
     */
    fun getBooksDetailsFromApi(id: Int) = CoroutineScope(Dispatchers.IO).launch {

        val service = kotlin.runCatching { myRetrofit.fetchOneBook(id) }
        service.onSuccess {
            when(it.code()) {

                in 200..299 -> it.body()?.let { details ->
                    myBookDao.insertOneBookDetails(details)
                }

                in 300..599 -> Log.e("ERROR", it.errorBody().toString())
                else -> Log.d("ERROR", it.errorBody().toString())

            }
        }

        service.onFailure {

            Log.e("ERROR", it.message.toString())

        }

    }

}