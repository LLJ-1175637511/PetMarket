package com.qyl.petmarket.data.bean

data class DynamicBean(
    val dynamicContent: String?,
    val dynamicKind: String,
    val dynamicPicture: String?,
    val headPortrait: String,
    val id: Int,
    val isLiked: Boolean,
    val likedNums: Int,
    val petKind: String?,
    val publishTime: String,
    val author: String
)