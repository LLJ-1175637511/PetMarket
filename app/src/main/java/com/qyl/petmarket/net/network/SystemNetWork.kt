package com.qyl.petmarket.net.network

import com.qyl.petmarket.net.RetrofitCreator
import com.qyl.petmarket.net.server.UserServer
import okhttp3.MultipartBody
import retrofit2.await

object SystemNetWork {

    private val server by lazy { RetrofitCreator.create<UserServer>() }

    suspend fun login(map: Map<String, String>) = server.login(map).await()

    suspend fun register(map: Map<String, String>,photo: MultipartBody.Part) = server.register(map,photo).await()

}