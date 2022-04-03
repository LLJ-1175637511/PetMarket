package com.lyc.epidemiccontrol.ext

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lyc.epidemiccontrol.data.bean.LoginBean
import com.lyc.epidemiccontrol.net.BaseBean
import com.lyc.epidemiccontrol.utils.Const
import com.lyc.epidemiccontrol.utils.ECLib
import com.lyc.epidemiccontrol.utils.LogUtils
import com.lyc.epidemiccontrol.utils.ToastUtils
import kotlinx.coroutines.withContext

const val NET_DATA_TAG = "NET_CURRENT_DATA"
const val NET_EXC_TAG = "NET_EXCEPTION_DATA"

fun SharedPreferences.save(block: SharedPreferences.Editor.() -> Unit): Boolean {
    val edit = edit()
    block(edit)
    return edit.commit()
}

fun Int.isCodeSuc(): Boolean = this == 0

suspend inline fun <reified T> AppCompatActivity.fastRequest(
    isLogin:Boolean = false,
    crossinline block:suspend () -> BaseBean
):T? = withContext(this.lifecycleScope.coroutineContext){
    var data: T? = null
    runCatching {
        val baseBean = block()
        LogUtils.d(NET_DATA_TAG, baseBean.toString())
        if (baseBean.code.isCodeSuc()) {
            data = baseConverter<T>(baseBean)
            if (isLogin){
                ECLib.getSP(Const.SPNet).save {
                    putString(Const.SPNetToken,baseBean.message)
                }
            }
        }else {
            throw Exception("请求错误 --> errCode:${baseBean.code} errMsg:${baseBean.message}")
        }
    }.onFailure {
        val exc = Exception(it)
        LogUtils.d(NET_EXC_TAG, "网络错误:${exc.message}")
        ToastUtils.toastShort(exc.message.toString())
    }
    data
}

suspend inline fun <reified T> ViewModel.fastRequest(
    crossinline block: suspend () -> BaseBean
):T? = withContext(this.viewModelScope.coroutineContext){
    var data: T? = null
    runCatching {
        val baseBean = block()
        LogUtils.d(NET_DATA_TAG, baseBean.toString())
        if (baseBean.code.isCodeSuc()) {
            data = baseConverter<T>(baseBean)
        }else {
            throw Exception("请求错误 --> errCode:${baseBean.code} errMsg:${baseBean.message}")
        }
    }.onFailure {
        val exc = Exception(it)
        LogUtils.d(NET_EXC_TAG, "网络错误:${exc.message}")
        ToastUtils.toastShort(exc.message.toString())
    }
    data
}

inline fun <reified T> baseConverter(bb:BaseBean): T {
    val gson = Gson()
    val type = object : TypeToken<T>() {}.type
    return gson.fromJson(bb.data, type) as T
}

