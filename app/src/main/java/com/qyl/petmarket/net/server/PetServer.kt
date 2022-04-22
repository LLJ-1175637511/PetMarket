package com.qyl.petmarket.net.server

import com.qyl.petmarket.net.BaseBean
import com.qyl.petmarket.net.config.SysNetConfig
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface PetServer {

    @GET("Petinfo/GetPetInfo")
    fun findPet(@Query(SysNetConfig.UserName) username: String): Call<BaseBean>

    @Multipart
    @POST("Petinfo/AddPetInfo")
    fun addPet(
        @QueryMap map: Map<String, String>,
        @Part photo: MultipartBody.Part
    ): Call<BaseBean>



}