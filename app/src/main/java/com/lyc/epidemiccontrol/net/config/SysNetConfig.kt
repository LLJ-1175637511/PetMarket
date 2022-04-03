package com.lyc.epidemiccontrol.net.config

object SysNetConfig {

    const val UserNum = "UserNum" //用户名
    const val UserPwd = "UserPwd" //用户密码
    const val Imei = "imei" //用户设备ID
    const val Lat = "lat" //用户设备ID
    const val Lng = "lng" //用户设备ID

    const val MULTIPART_TEXT = "text/plain"
    const val MULTIPART_FILE = "multipart/form-data"

    fun buildLoginMap(
        user: String,
        pass: String
    ) = mapOf(UserNum to user, UserPwd to pass)

}