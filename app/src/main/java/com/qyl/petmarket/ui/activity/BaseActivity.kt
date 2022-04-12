package com.qyl.petmarket.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.qyl.petmarket.ext.*
import com.qyl.petmarket.net.BaseBean
import com.qyl.petmarket.utils.Const
import com.qyl.petmarket.utils.ECLib
import com.qyl.petmarket.utils.LogUtils
import com.qyl.petmarket.utils.ToastUtils
import kotlinx.coroutines.withContext

abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity() {

    abstract fun getLayoutId(): Int

    lateinit var mDataBinding: DB

    open fun init() {}

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        init()
    }

    fun <T : Activity> startCommonActivity(activity: Class<T>) {
        startActivity(Intent(this, activity))
    }

    inline fun <reified T : Activity> startCommonActivity() {
        startCommonActivity(T::class.java)
    }

    fun <T : Activity> startActivityAndFinish(activity: Class<T>) {
        startActivity(Intent(this, activity))
        finish()
    }

    inline fun <reified T : Activity> startActivityAndFinish() {
        startCommonActivity(T::class.java)
        finish()
    }

    private fun initView() {
        mDataBinding = DataBindingUtil.setContentView<DB>(this, getLayoutId())
        mDataBinding.lifecycleOwner = this //初始化生命周期
    }

    fun hideKeyBoard(){
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    suspend inline fun <reified T> fastRequest(
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

    override fun onDestroy() {
        super.onDestroy()
        //界面销毁后 解绑dataBinding
        if (this::mDataBinding.isInitialized) mDataBinding.unbind()
    }

}