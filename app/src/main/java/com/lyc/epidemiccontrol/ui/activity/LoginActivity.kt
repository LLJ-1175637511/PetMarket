package com.lyc.epidemiccontrol.ui.activity

import android.Manifest
import android.app.Activity
import androidx.lifecycle.lifecycleScope
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.bean.LoginBean
import com.lyc.epidemiccontrol.databinding.ActivityLoginBinding
import com.lyc.epidemiccontrol.ext.baseConverter
import com.lyc.epidemiccontrol.ext.fastRequest
import com.lyc.epidemiccontrol.net.config.SysNetConfig
import com.lyc.epidemiccontrol.net.repository.SystemRepository
import com.lyc.epidemiccontrol.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : BaseLoginActivity<ActivityLoginBinding>() {

    override fun getLayoutId() = R.layout.activity_login

    override fun initPermission() = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun init() {
        super.init()
        mDataBinding.apply {
            etUserNameLogin.setText(getUserInfo().first)
            etUserPwdLogin.setText(getUserInfo().second)
            btLogin.setOnClickListener {
                login(
                    etUserNameLogin.text.toString(),
                    etUserPwdLogin.text.toString(),
                    MainActivity::class.java
                )
            }
            ivLoginWeibo.setOnClickListener {
                ToastUtils.toastShort("第三方登录功能开发中")
            }
        }
    }

    private fun <T : Activity> login(username: String, password: String, target: Class<T>) {
        if (username.isEmpty()) {
            ToastUtils.toastShort("用户名不能为空")
            return
        }
        if (password.isEmpty()) {
            ToastUtils.toastShort("密码不能为空")
            return
        }
        lifecycleScope.launch {
            fastRequest<LoginBean>(true) {
                SystemRepository.loginRequest(
                    SysNetConfig.buildLoginMap(
                        username, password
                    )
                )
            }?.let {
                savedUserPwdSp(username, password,"")
                startActivityAndFinish<MainActivity>()
            }
        }
    }

}