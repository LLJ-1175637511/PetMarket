package com.qyl.petmarket.data.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginBean(
    val dynamicNum: Int,
    val eamil: String,
    val gender: String,
    val headPortrait: String,
    val likedNums: Int,
    val preference: List<String>,
    val telephone: String,
    val userName: String
): Parcelable