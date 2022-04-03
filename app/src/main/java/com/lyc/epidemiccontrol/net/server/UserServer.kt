package com.lyc.epidemiccontrol.net.server

import com.lyc.epidemiccontrol.net.BaseBean
import retrofit2.Call
import retrofit2.http.*

interface UserServer {

    @GET("Login/GetLoginUser")
    fun login(@QueryMap map: Map<String, String>): Call<BaseBean>

}