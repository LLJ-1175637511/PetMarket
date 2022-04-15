package com.qyl.petmarket.ui.activity.user

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import cn.leancloud.LCUser
import com.qyl.petmarket.data.bean.LoginBean
import com.qyl.petmarket.ext.save
import com.qyl.petmarket.net.NetActivity
import com.qyl.petmarket.net.config.SysNetConfig
import com.qyl.petmarket.net.repository.SystemRepository
import com.qyl.petmarket.ui.activity.BaseActivity
import com.qyl.petmarket.utils.Const
import com.qyl.petmarket.utils.ECLib
import com.qyl.petmarket.utils.LCUtils
import com.qyl.petmarket.utils.ToastUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.launch


abstract class BaseLoginActivity<DB : ViewDataBinding> : NetActivity<DB>() {

    override fun init() {
        super.init()
        //检查权限
        checkPermission()
        //检查用户名密码
        loadUserData()
    }

    private fun checkPermission() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                initPermission(),
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    abstract fun initPermission(): Array<String>

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                ToastUtils.toastShort("权限未允许")
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = initPermission().all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    /**
     * ----------------------------------------------------------------------------------------------
     * */

    private var mUserName = ""
    private var mPassWord = ""

    fun getUserInfo() = Pair(mUserName, mPassWord)

    fun <T : Activity> login(username: String, password: String, target: Class<T>) {
        if (username.isEmpty()) {
            ToastUtils.toastShort("用户名不能为空")
            return
        }
        if (password.isEmpty()) {
            ToastUtils.toastShort("密码不能为空")
            return
        }
        kotlin.runCatching {
            lifecycleScope.launch {
                fastRequest<LoginBean>() {
                    SystemRepository.loginRequest(
                        SysNetConfig.buildLoginMap(
                            username, password
                        )
                    )
                }?.let {
                    savedSp(username, password)
                    startActivityAndFinish(target)
                }
            }
        }.onFailure {
            ToastUtils.toastShort("登录失败 ${it.message}")
        }
    }

    private fun loadUserData() {
        ECLib.getSP(Const.SPUser).let { sp ->
            if (sp.contains(Const.SPUserName)) {
                mUserName = sp.getString(Const.SPUserName, "").toString()
            }
            if (sp.contains(Const.SPUserPwd)) {
                mPassWord = sp.getString(Const.SPUserPwd, "").toString()
            }
        }
    }

    /**
     * 保存用户名 密码
     */
    private fun savedSp(name: String, pwd: String) {
        ECLib.getSP(Const.SPUser).save {
            putString(Const.SPUserName, name)
            putString(Const.SPUserPwd, pwd)
        }
    }

}