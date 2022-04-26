package com.qyl.petmarket.net.server

import com.qyl.petmarket.net.BaseBean
import com.qyl.petmarket.net.config.SysNetConfig
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface UserServer {

    @GET("Account/Login")
    fun login(@QueryMap map: Map<String, String>): Call<BaseBean>

    @GET("Account/GetUser")
    fun getUser(@QueryMap map: Map<String, String>): Call<BaseBean>

    @PUT("Account/UpdatePreference")
    fun preference(
        @Query(SysNetConfig.Preference) preference: String,
        @Query(SysNetConfig.UserName) username: String
    ): Call<BaseBean>

    @Multipart
    @POST("Account/AddUser")
    fun register(
        @QueryMap map: Map<String, String>,
        @Part photo: MultipartBody.Part
    ): Call<BaseBean>


}