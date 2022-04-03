package com.lyc.epidemiccontrol.net.network

import com.lyc.epidemiccontrol.net.RetrofitCreator
import com.lyc.epidemiccontrol.net.server.UserServer
import retrofit2.await

object SystemNetWork {

    private val userServer by lazy { RetrofitCreator.create<UserServer>() }

    suspend fun login(map: Map<String, String>) = userServer.login(map).await()

}