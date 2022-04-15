package com.qyl.petmarket.ui.activity.user

import android.Manifest
import com.qyl.petmarket.R
import com.qyl.petmarket.databinding.ActivityLoginBinding
import com.qyl.petmarket.ext.save
import com.qyl.petmarket.ui.activity.MainActivity
import com.qyl.petmarket.utils.Const
import com.qyl.petmarket.utils.ECLib
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
                val sp = ECLib.getSP(Const.SPUser)
                val c = if (sp.contains(Const.SPHadChooseHobby)){
                    MainActivity::class.java
                }else{
                    ChooseHobbyActivity::class.java
                }
                login(
                    etUserNameLogin.text.toString(),
                    etUserPwdLogin.text.toString(),
                    c
                )
            }
            ivLoginWeibo.setOnClickListener {
                ToastUtils.toastShort("第三方登录功能开发中")
            }
        }
        mDataBinding.tvRegister.setOnClickListener {
            startCommonActivity<RegisterActivity>()
        }
    }

    companion object{
        const val userInfo = "userInfo"
    }
}