package com.qyl.petmarket.net.config

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.qyl.petmarket.utils.Const
import com.qyl.petmarket.utils.ECLib
import com.qyl.petmarket.utils.PhotoUtils
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.size
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

object SysNetConfig {

    const val UserPwd = "UserPwd" //用户密码
    const val UserName = "UserName"
    const val Preference = "Preference"
    const val Gender = "Gender"
    const val Telephone = "Telephone"
    const val Eamil = "Eamil"

    const val PetName = "PetName"
    const val Birthday = "Birthday"
    const val Like = "Like"
    const val Taboo = "Taboo"
    const val ID = "Id"
    const val Content = "Content"

    const val DynamicKind = "DynamicKind"
    const val PetKind = "PetKind"
    const val Author = "Author"
    const val DynamicContent = "DynamicContent"

    const val HeadPortrait = "HeadPortrait"
    const val PetPicture = "PetPicture"
    const val DynamicPicture = "DynamicPicture"
    const val DynamicId = "DynamicId"

    const val MULTIPART_TEXT = "text/plain"
    const val MULTIPART_FILE = "multipart/form-data"

    fun getUserName() = ECLib.getSP(Const.SPUser).getString(Const.SPUserName, "").toString()

    fun buildLoginMap(
        user: String,
        pass: String
    ) = mapOf(UserName to user, UserPwd to pass)

    fun buildRegisterMap(
        username: String,
        password: String,
        email: String,
        phone: String,
        sex: String
    ) = mapOf(
        UserName to username,
        UserPwd to password,
        Eamil to email,
        Telephone to phone,
        Gender to sex,
    )

    fun buildAddPetMap(
        petName: String,
        birthday: String,
        like: String,
        taboo: String,
    ) = mapOf(
        UserName to getUserName(),
        PetName to petName,
        Birthday to birthday,
        Like to like,
        Taboo to taboo,
    )

    fun buildAddDynamicMap(
        dynamicKind: String,
        petKind: String?,
        dynamicContent: String?,
    ): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map.apply {
            petKind?.let {
                put(PetKind, it)
            }
            dynamicContent?.let {
                put(DynamicContent, it)
            }
            put(DynamicKind, dynamicKind)
            put(Author, getUserName())
        }
        return map
    }

    fun buildLikeDynamic(id: Int) = mapOf(
        UserName to getUserName(),
        DynamicId to id.toString()
    )

    fun buildUserDynamic(author: String? = null) = mutableMapOf<String, String>().apply {
        put(UserName, getUserName())
        author?.let {
            put(Author, it)
        }
    }

    fun buildQueryDynamicMap(
        author: String?,
        dynamicKind: String?,
        searchContent: String?,
    ): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map.apply {
            author?.let {
                put(Author, it)
            }
            dynamicKind?.let {
                put(DynamicKind, it)
            }
            searchContent?.let {
                put(Content, it)
            }
            put(UserName, getUserName())
        }
        return map
    }

    fun buildUpdatePetMap(
        id: Int,
        petName: String,
        birthday: String,
        like: String,
        taboo: String,
    ) = mapOf(
        UserName to getUserName(),
        ID to id.toString(),
        PetName to petName,
        Birthday to birthday,
        Like to like,
        Taboo to taboo,
    )

    suspend fun buildPhotoPart(context: Context, uri: Uri, paramsName: String): MultipartBody.Part {
        val path = PhotoUtils.getFileAbsolutePath(context, uri)
        val faceFile = File(path)

        if (!faceFile.exists()) throw Exception("图片缺失")

        val compressedImageFile = Compressor.compress(context, faceFile) {
            quality(50)
            format(Bitmap.CompressFormat.JPEG)
            size(512_152)
        }

        val fmt = MULTIPART_FILE.toMediaTypeOrNull()

        val faceRequest = RequestBody.create(fmt, compressedImageFile)
        //注意字段名
        return MultipartBody.Part.createFormData(paramsName, compressedImageFile.name, faceRequest)
    }

}