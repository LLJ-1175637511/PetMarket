package com.qyl.petmarket.net.network

import com.qyl.petmarket.net.RetrofitCreator
import com.qyl.petmarket.net.config.SysNetConfig
import com.qyl.petmarket.net.server.DynamicServer
import com.qyl.petmarket.net.server.PetServer
import com.qyl.petmarket.net.server.UserServer
import okhttp3.MultipartBody
import retrofit2.await

object SystemNetWork {

    private val userServer by lazy { RetrofitCreator.create<UserServer>() }

    private val petServer by lazy { RetrofitCreator.create<PetServer>() }

    private val dynamicServer by lazy { RetrofitCreator.create<DynamicServer>() }

    suspend fun login(map: Map<String, String>) = userServer.login(map).await()

    suspend fun preference(preference: String, username: String = SysNetConfig.getUserName()) =
        userServer.preference(preference, username).await()

    suspend fun register(map: Map<String, String>, photo: MultipartBody.Part) =
        userServer.register(map, photo).await()

    suspend fun addPet(map: Map<String, String>, photo: MultipartBody.Part) =
        petServer.addPet(map, photo).await()

    suspend fun addDynamic(map: Map<String, String>, photo: MultipartBody.Part) =
        dynamicServer.addDynamic(map, photo).await()

    suspend fun addDynamic(map: Map<String, String>) =
        dynamicServer.addDynamic(map).await()

 suspend fun findDynamic(map: Map<String, String>) =
        dynamicServer.findDynamic(map).await()

    suspend fun findPet(username: String = SysNetConfig.getUserName()) =
        petServer.findPet(username).await()

    suspend fun deletePet(id: Int) = petServer.deletePet(id).await()

    suspend fun updatePet(map: Map<String, String>, photo: MultipartBody.Part) =
        petServer.updatePet(map, photo).await()

    suspend fun updatePet(map: Map<String, String>) = petServer.updatePet(map).await()

}