package com.lyc.epidemiccontrol.ui.activity

import android.Manifest
import android.app.Activity
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.databinding.ActivityLoginBinding
import com.lyc.epidemiccontrol.utils.ToastUtils

class LoginActivity : BaseLoginActivity<ActivityLoginBinding>() {

    override fun getLayoutId() = R.layout.activity_login

    override fun initPermission() = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.VIBRATE
    )

    override fun init() {
        super.init()
        mDataBinding.apply {
            etUserNameLogin.setText(getUserInfo().first)
            etUserPwdLogin.setText(getUserInfo().second)
            btLogin.setOnClickListener {
                login(etUserNameLogin.text.toString(),etUserPwdLogin.text.toString(), MainActivity::class.java)
            }
            ivLoginWeibo.setOnClickListener {
                ToastUtils.toastShort("第三方登录功能开发中")
            }
        }
    }

    private fun <T: Activity>login(username:String, password:String, target: Class<T>){
        if (username.isEmpty()) {
            ToastUtils.toastShort("用户名不能为空")
            return
        }
        if (password.isEmpty()) {
            ToastUtils.toastShort("密码不能为空")
            return
        }
    }

}