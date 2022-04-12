package com.qyl.petmarket.net.repository

import com.qyl.petmarket.net.network.SystemNetWork

object SystemRepository {

    suspend fun loginRequest(map: Map<String, String>) = SystemNetWork.login(map)

    suspend fun registerRequest(map: Map<String, String>) = SystemNetWork.register(map)

}