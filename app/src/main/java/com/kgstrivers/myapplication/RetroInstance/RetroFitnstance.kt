package com.kgstrivers.myapplication.RetroInstance

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

class RetroFitnstance {
    companion object
    {
        val baseurl = "https://my-json-server.typicode.com/iranjith4/"
        fun getretroInstance(): Retrofit {

            val log = HttpLoggingInterceptor()
            log.level = HttpLoggingInterceptor.Level.BODY
            val client  = OkHttpClient.Builder()
            client.addInterceptor(log)


            return  Retrofit.Builder().
            addConverterFactory(GsonConverterFactory.create()).
            baseUrl(baseurl).client(client.build())
                .build()

        }

    }
}