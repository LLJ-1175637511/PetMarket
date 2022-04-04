package com.lyc.epidemiccontrol.net.network

import com.lyc.epidemiccontrol.net.RetrofitCreator
import com.lyc.epidemiccontrol.net.server.UserServer
import retrofit2.await

object SystemNetWork {

    private val server by lazy { RetrofitCreator.create<UserServer>() }

    suspend fun login(map: Map<String, String>) = server.login(map).await()

    suspend fun register(map: Map<String, String>) = server.register(map).await()

}