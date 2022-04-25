package com.qyl.petmarket.net.server

import com.qyl.petmarket.net.BaseBean
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface DynamicServer {

    @Multipart
    @POST("Dynamic/AddDynamicInfo")
    fun addDynamic(
        @QueryMap map: Map<String, String>,
        @Part photo: MultipartBody.Part
    ): Call<BaseBean>

    @POST("Dynamic/AddDynamicInfo")
    fun addDynamic(
        @QueryMap map: Map<String, String>
    ): Call<BaseBean>

    @GET("Dynamic/GetDynamic")
    fun findDynamic(
        @QueryMap map: Map<String, String>
    ): Call<BaseBean>

}