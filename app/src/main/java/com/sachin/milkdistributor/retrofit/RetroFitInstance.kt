package com.sachin.milkdistributor.retrofit

import com.sachin.milkdistributor.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroFitInstance {

    companion object {
        private const val baseUrl = BASE_URL

        fun getRetroInstance(): Retrofit {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)

            return Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .client(client.build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
        }
    }
}