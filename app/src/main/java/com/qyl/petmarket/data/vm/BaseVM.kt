package com.qyl.petmarket.data.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qyl.petmarket.ext.NET_DATA_TAG
import com.qyl.petmarket.ext.NET_EXC_TAG
import com.qyl.petmarket.ext.baseConverter
import com.qyl.petmarket.ext.isCodeSuc
import com.qyl.petmarket.net.BaseBean
import com.qyl.petmarket.utils.LogUtils
import com.qyl.petmarket.utils.ToastUtils
import kotlinx.coroutines.withContext

abstract class BaseVM :ViewModel(){
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
}