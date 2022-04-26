package com.qyl.petmarket.net.server

import com.qyl.petmarket.net.BaseBean
import com.qyl.petmarket.net.config.SysNetConfig
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

    @DELETE("Dynamic/DeleteDynamicInfo")
    fun deleteDynamic(
        @Query(SysNetConfig.ID) id:Int
    ): Call<BaseBean>

    @GET("Dynamic/GetDynamic")
    fun findDynamic(
        @QueryMap map: Map<String, String>
    ): Call<BaseBean>

    @PUT("Dynamic/LikeParallelism")
    fun likeDynamic(
        @QueryMap map: Map<String, String>
    ): Call<BaseBean>

    @GET("Dynamic/LikeParallelism")
    fun queryLikeRecord(
        @QueryMap map: Map<String, String>
    ): Call<BaseBean>


}