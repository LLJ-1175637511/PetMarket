package com.qyl.petmarket.ui.activity

import android.Manifest
import android.app.Activity
import com.qyl.petmarket.R
import com.qyl.petmarket.databinding.ActivityLoginBinding
import com.qyl.petmarket.utils.ToastUtils

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
        mDataBinding.tvRegister.setOnClickListener {
            startActivityAndFinish<RegisterActivity>()
        }
    }

    companion object{
        const val userInfo = "userInfo"
    }
}