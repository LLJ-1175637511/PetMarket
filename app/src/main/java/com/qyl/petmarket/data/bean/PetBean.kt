package com.qyl.petmarket.data.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PetBean(
    val birthday: String,
    val id: Int,
    val like: String?,
    val petName: String,
    val petPicture: String,
    val taboo: String?,
    val userName: String
):Parcelable