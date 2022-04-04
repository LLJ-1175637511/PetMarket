package com.lyc.epidemiccontrol.ui.activity

import androidx.lifecycle.lifecycleScope
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.databinding.ActivityRegisterBinding
import com.lyc.epidemiccontrol.net.config.SysNetConfig
import com.lyc.epidemiccontrol.net.repository.SystemRepository
import com.lyc.epidemiccontrol.utils.ToastUtils
import kotlinx.coroutines.launch

class RegisterActivity:BaseActivity<ActivityRegisterBinding>() {

    override fun getLayoutId() = R.layout.activity_register

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
        mDataBinding.toolbar.toolbarBaseTitle.text = "账号注册"
        mDataBinding.tvRegister.setOnClickListener {
            kotlin.runCatching {
                val username = mDataBinding.username.text.toString()
                if (username.isEmpty()) toastErr(mDataBinding.address.hint.toString())

                val userNumber = mDataBinding.userNumber.text.toString()
                if (userNumber.isEmpty()) toastErr(mDataBinding.userNumber.hint.toString())

                val password = mDataBinding.password.text.toString()
                if (password.isEmpty()) toastErr(mDataBinding.password.hint.toString())

                val confirmPassword = mDataBinding.confirmPassword.text.toString()
                if (confirmPassword.isEmpty()) toastErr(mDataBinding.confirmPassword.hint.toString())

                val email = mDataBinding.email.text.toString()
                if (email.isEmpty()) toastErr(mDataBinding.email.hint.toString())

                val phone = mDataBinding.phone.text.toString()
                if (phone.isEmpty()) toastErr(mDataBinding.phone.hint.toString())

                val address = mDataBinding.address.text.toString()
                if (address.isEmpty()) toastErr(mDataBinding.address.hint.toString())

                val sex = if (mDataBinding.radioGroup.checkedRadioButtonId == R.id.rb1){
                    mDataBinding.rb1.text.toString()
                }else{
                    mDataBinding.rb2.text.toString()
                }

                lifecycleScope.launch {
                    val map = SysNetConfig.buildRegisterMap(username,userNumber,password,email,phone,address,sex)
                    SystemRepository.registerRequest(map)
                }
            }
        }
    }

    private fun toastErr(s:String){
        ToastUtils.toastShort("${s}不能为空")
        throw Exception()
    }

}