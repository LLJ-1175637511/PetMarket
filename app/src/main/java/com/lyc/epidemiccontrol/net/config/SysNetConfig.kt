package com.lyc.epidemiccontrol.net.config

import android.text.Editable

object SysNetConfig {

    const val UserNum = "UserNum" //用户名
    const val UserPwd = "UserPwd" //用户密码
    const val UserName = "UserName"
    const val Gender = "Gender"
    const val Address = "Address"
    const val Telephone = "Telephone"
    const val Eamil = "Eamil"

    const val MULTIPART_TEXT = "text/plain"
    const val MULTIPART_FILE = "multipart/form-data"

    fun buildLoginMap(
        user: String,
        pass: String
    ) = mapOf(UserNum to user, UserPwd to pass)

    fun buildRegisterMap(
        username: String,
        userNumber: String,
        password: String,
        email: String,
        phone: String,
        address: String,
        sex: String
    ) = mapOf(
        UserName to username,
        UserNum to userNumber,
        UserPwd to password,
        Eamil to email,
        Telephone to phone,
        Address to address,
        Gender to sex,
    )

}