package com.qyl.petmarket.net.server

import com.qyl.petmarket.net.BaseBean
import retrofit2.Call
import retrofit2.http.*

interface UserServer {

    @GET("Login/GetLoginUser")
    fun login(@QueryMap map: Map<String, String>): Call<BaseBean>

    @FormUrlEncoded
    @POST("Account/CreateUser")
    fun register(@QueryMap map: Map<String, String>): Call<BaseBean>

}