package com.lyc.epidemiccontrol.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitCreator {

    private const val mysqlUrl = "http://39.105.114.212:5298/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mysqlUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}