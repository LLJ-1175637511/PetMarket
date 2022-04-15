package com.qyl.petmarket.net.server

import com.qyl.petmarket.net.BaseBean
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface UserServer {

    @GET("Account/Login")
    fun login(@QueryMap map: Map<String, String>): Call<BaseBean>

    @Multipart
    @POST("Account/AddUser")
    fun register(@QueryMap map: Map<String, String>,@Part photo: MultipartBody.Part): Call<BaseBean>

}