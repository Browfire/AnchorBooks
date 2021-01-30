package com.example.anchorbooks.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookRetrofitClient {

    companion object {
        private const val BASE_URL = "https://my-json-server.typicode.com/Himuravidal/anchorBooks/"

        fun retrofitInstance(): BookApi {
            val retrofitClient = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofitClient.create(BookApi::class.java)
        }
    }

}