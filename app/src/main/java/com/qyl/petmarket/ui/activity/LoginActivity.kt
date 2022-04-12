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

    private fun <T : Activity> login(username: String, password: String, target: Class<T>) {
        if (username.isEmpty()) {
            ToastUtils.toastShort("用户名不能为空")
            return
        }
        if (password.isEmpty()) {
            ToastUtils.toastShort("密码不能为空")
            return
        }
        startActivityAndFinish<MainActivity>()
        /*lifecycleScope.launch {
            fastRequest<LoginBean>(true) {
                SystemRepository.loginRequest(
                    SysNetConfig.buildLoginMap(
                        username, password
                    )
                )
            }?.let {
                savedUserPwdSp(username, password,it.userNum)
                val i = Intent(this@LoginActivity,MainActivity::class.java)
                i.putExtra(userInfo,it)
                startActivity(i)
                finish()
            }
        }*/
    }

    companion object{
        const val userInfo = "userInfo"
    }
}