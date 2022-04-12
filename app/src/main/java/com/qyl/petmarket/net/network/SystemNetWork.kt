package com.qyl.petmarket.net.network

import com.qyl.petmarket.net.RetrofitCreator
import com.qyl.petmarket.net.server.UserServer
import retrofit2.await

object SystemNetWork {

    private val server by lazy { RetrofitCreator.create<UserServer>() }

    suspend fun login(map: Map<String, String>) = server.login(map).await()

    suspend fun register(map: Map<String, String>) = server.register(map).await()

}