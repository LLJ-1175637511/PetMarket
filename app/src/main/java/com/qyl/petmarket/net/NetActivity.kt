package com.qyl.petmarket.net

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.qyl.petmarket.data.bean.LoginBean
import com.qyl.petmarket.ext.NET_DATA_TAG
import com.qyl.petmarket.ext.NET_EXC_TAG
import com.qyl.petmarket.ext.baseConverter
import com.qyl.petmarket.ext.isCodeSuc
import com.qyl.petmarket.net.config.SysNetConfig
import com.qyl.petmarket.net.repository.SystemRepository
import com.qyl.petmarket.ui.activity.BaseActivity
import com.qyl.petmarket.utils.Const
import com.qyl.petmarket.utils.ECLib
import com.qyl.petmarket.utils.LogUtils
import com.qyl.petmarket.utils.ToastUtils
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class NetActivity<DB : ViewDataBinding> : BaseActivity<DB>() {

    suspend inline fun <reified T> fastRequest(
        crossinline block: suspend () -> BaseBean
    ): T? = withContext(this.lifecycleScope.coroutineContext) {
        var data: T? = null
        runCatching {
            val baseBean = block()
            LogUtils.d(NET_DATA_TAG, baseBean.toString())
            if (baseBean.code.isCodeSuc()) {
                data = baseConverter<T>(baseBean)
            } else {
                throw Exception("请求错误 --> errCode:${baseBean.code} errMsg:${baseBean.message}")
            }
        }.onFailure {
            val exc = Exception(it)
            LogUtils.d(NET_EXC_TAG, "网络错误:${exc.message}")
            ToastUtils.toastShort(exc.message.toString())
        }
        data
    }

    fun getUserData(
        username: String = ECLib.getSP(Const.SPUser).getString(Const.SPUserName, "") ?: "",
        password: String = ECLib.getSP(Const.SPUser).getString(Const.SPUserPwd, "") ?: "",
        block: (data: LoginBean) -> Unit
    ) {
        lifecycleScope.launch {
            fastRequest<LoginBean> {
                SystemRepository.loginRequest(
                    SysNetConfig.buildLoginMap(
                        username, password
                    )
                )
            }?.let {
                block(it)
            }
        }
    }
}