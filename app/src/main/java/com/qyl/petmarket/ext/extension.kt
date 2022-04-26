package com.qyl.petmarket.ext

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qyl.petmarket.net.BaseBean

const val NET_DATA_TAG = "NET_CURRENT_DATA"
const val NET_EXC_TAG = "NET_EXCEPTION_DATA"

fun SharedPreferences.save(block: SharedPreferences.Editor.() -> Unit): Boolean {
    val edit = edit()
    block(edit)
    return edit.commit()
}

fun Int.isCodeSuc(): Boolean = this == 0

inline fun <reified T> baseConverter(bb:BaseBean): T {
    val gson = Gson()
    val type = object : TypeToken<T>() {}.type
    return gson.fromJson(bb.data, type) as T
}

fun Any?.string(): String = this?.toString() ?: ""
fun Any?.int(): Int = this?.toString()?.toInt() ?: 0
fun Any?.long(): Long = this?.toString()?.toLong() ?: System.currentTimeMillis()
fun Any?.boolean(): Boolean = this?.toString()?.toBoolean() ?: false
