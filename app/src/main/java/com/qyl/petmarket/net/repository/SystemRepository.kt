package com.qyl.petmarket.net.repository

import com.qyl.petmarket.net.network.SystemNetWork
import okhttp3.MultipartBody

object SystemRepository {

    suspend fun loginRequest(map: Map<String, String>) = SystemNetWork.login(map)

    suspend fun preferenceRequest(preference: String) = SystemNetWork.preference(preference)

    suspend fun registerRequest(map: Map<String, String>,photo: MultipartBody.Part) = SystemNetWork.register(map,photo)

    suspend fun addPetRequest(map: Map<String, String>,photo: MultipartBody.Part) = SystemNetWork.addPet(map,photo)

    suspend fun findPetRequest() = SystemNetWork.findPet()

    suspend fun deletePetRequest(id:Int) = SystemNetWork.deletePet(id)

    suspend fun updatePetRequest(map: Map<String, String>,photo: MultipartBody.Part) = SystemNetWork.updatePet(map,photo)
    suspend fun updatePetRequest(map: Map<String, String>) = SystemNetWork.updatePet(map)

    suspend fun addDynamicRequest(map: Map<String, String>,photo: MultipartBody.Part) = SystemNetWork.addDynamic(map,photo)
    suspend fun addDynamicRequest(map: Map<String, String>) = SystemNetWork.addDynamic(map)

    suspend fun findDynamicRequest(map: Map<String, String>) = SystemNetWork.findDynamic(map)
    suspend fun likeDynamicRequest(map: Map<String, String>) = SystemNetWork.likeDynamic(map)

}