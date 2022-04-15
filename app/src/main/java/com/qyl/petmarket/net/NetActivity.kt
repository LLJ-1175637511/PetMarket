package com.qyl.petmarket.net

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.qyl.petmarket.ext.*
import com.qyl.petmarket.ui.activity.BaseActivity
import com.qyl.petmarket.utils.Const
import com.qyl.petmarket.utils.ECLib
import com.qyl.petmarket.utils.LogUtils
import com.qyl.petmarket.utils.ToastUtils
import kotlinx.coroutines.withContext

abstract class NetActivity<DB:ViewDataBinding>: BaseActivity<DB>() {

    suspend inline fun <reified T> fastRequest(
        crossinline block:suspend () -> BaseBean
    ):T? = withContext(this.lifecycleScope.coroutineContext){
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