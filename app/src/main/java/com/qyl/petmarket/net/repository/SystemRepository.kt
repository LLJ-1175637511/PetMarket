package com.qyl.petmarket.net.repository

import com.qyl.petmarket.net.network.SystemNetWork
import okhttp3.MultipartBody

object SystemRepository {

    suspend fun loginRequest(map: Map<String, String>) = SystemNetWork.login(map)

    suspend fun preferenceRequest(preference: String) = SystemNetWork.preference(preference)

    suspend fun registerRequest(map: Map<String, String>,photo: MultipartBody.Part) = SystemNetWork.register(map,photo)

    suspend fun addPetRequest(map: Map<String, String>,photo: MultipartBody.Part) = SystemNetWork.addPet(map,photo)

    suspend fun findPetRequest() = SystemNetWork.findPet()

}