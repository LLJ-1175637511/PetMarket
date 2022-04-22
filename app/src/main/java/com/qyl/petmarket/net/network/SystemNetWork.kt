package com.qyl.petmarket.net.network

import com.qyl.petmarket.net.RetrofitCreator
import com.qyl.petmarket.net.config.SysNetConfig
import com.qyl.petmarket.net.server.PetServer
import com.qyl.petmarket.net.server.UserServer
import okhttp3.MultipartBody
import retrofit2.await

object SystemNetWork {

    private val userServer by lazy { RetrofitCreator.create<UserServer>() }

    private val petServer by lazy { RetrofitCreator.create<PetServer>() }

    suspend fun login(map: Map<String, String>) = userServer.login(map).await()

    suspend fun preference(preference: String, username: String = SysNetConfig.getUserName()) =
        userServer.preference(preference, username).await()

    suspend fun register(map: Map<String, String>, photo: MultipartBody.Part) =
        userServer.register(map, photo).await()

    suspend fun addPet(map: Map<String, String>, photo: MultipartBody.Part) =
        petServer.addPet(map, photo).await()

    suspend fun findPet(username: String = SysNetConfig.getUserName()) =
        petServer.findPet(username).await()


}