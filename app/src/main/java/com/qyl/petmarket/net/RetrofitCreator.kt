package com.qyl.petmarket.net

import com.qyl.petmarket.utils.CommonUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitCreator {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://${CommonUtils.host}:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}