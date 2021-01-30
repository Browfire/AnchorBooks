package com.example.anchorbooks.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApi {

    @GET("books/")
    suspend fun fetchAllBooks(): Response<List<BookItem>>

    @GET("books/{id}")
    suspend fun fetchOneBook(@Path("id") id: Int): Response<BookDetails>

}