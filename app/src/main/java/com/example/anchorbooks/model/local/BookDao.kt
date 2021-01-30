package com.example.anchorbooks.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.anchorbooks.model.BookDetails
import com.example.anchorbooks.model.BookItem

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBookItems(list: List<BookItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneBookDetails(details: BookDetails)

    @Query("SELECT * FROM bookItem")
    fun getAllBookItems(): LiveData<List<BookItem>>

    @Query("SELECT * FROM bookDetails WHERE id=:id")
    fun getOneBookDetails(id: Int): LiveData<BookDetails>

}