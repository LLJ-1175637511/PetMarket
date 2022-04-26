package com.qyl.petmarket.ui.activity.user

import android.net.Uri
import android.os.Build
import androidx.lifecycle.lifecycleScope
import com.qyl.petmarket.R
import com.qyl.petmarket.databinding.ActivityRegisterBinding
import com.qyl.petmarket.ext.save
import com.qyl.petmarket.net.config.SysNetConfig
import com.qyl.petmarket.net.repository.SystemRepository
import com.qyl.petmarket.ui.activity.BaseAddPhotoActivity
import com.qyl.petmarket.utils.Const
import com.qyl.petmarket.utils.ECLib
import com.qyl.petmarket.utils.ToastUtils
import kotlinx.coroutines.launch

class RegisterActivity : BaseAddPhotoActivity<ActivityRegisterBinding>() {

    override fun getLayoutId() = R.layout.activity_register

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
        mDataBinding.toolbar.toolbarBaseTitle.text = "账号注册"
        buildLaunch {
            uri?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mDataBinding.tvChoosePhoto.setTextColor(getColor(R.color.grey))
                }
                mDataBinding.tvChoosePhoto.text = "头像选择成功"
            }
        }
        mDataBinding.tvChoosePhoto.setOnClickListener { v ->
            choosePhoto()
        }
        mDataBinding.tvRegister.setOnClickListener {
            kotlin.runCatching {
                val username = mDataBinding.username.text.toString()
                if (username.isEmpty()) toastErr(mDataBinding.username.hint.toString())

                val password = mDataBinding.password.text.toString()
                if (password.isEmpty()) toastErr(mDataBinding.password.hint.toString())

                val confirmPassword = mDataBinding.confirmPassword.text.toString()
                if (confirmPassword.isEmpty()) toastErr(mDataBinding.confirmPassword.hint.toString())

                val email = mDataBinding.email.text.toString()
                if (email.isEmpty()) toastErr(mDataBinding.email.hint.toString())

                val phone = mDataBinding.phone.text.toString()
                if (phone.isEmpty()) toastErr(mDataBinding.phone.hint.toString())

                if (uri == null) toastErr("头像")

                val sex = if (mDataBinding.radioGroup.checkedRadioButtonId == R.id.rb1) {
                    mDataBinding.rb1.text.toString()
                } else {
                    mDataBinding.rb2.text.toString()
                }
                lifecycleScope.launch {
                    val map = SysNetConfig.buildRegisterMap(username, password, email, phone, sex)
                    fastRequest<Boolean> {
                        val photo = SysNetConfig.buildPhotoPart(
                            this@RegisterActivity,
                            uri ?: Uri.EMPTY,
                            SysNetConfig.HeadPortrait
                        )
                        SystemRepository.registerRequest(map,photo)
                    }?.let {
                        ECLib.getSP(Const.SPUser).save {
                            putString(Const.SPUserName, username)
                            putString(Const.SPUserPwd, password)
                        }
                        startActivityAndFinish<LoginActivity>()
                    }
                }
            }
        }
    }

    private fun toastErr(s: String) {
        ToastUtils.toastShort("${s}不能为空")
        throw Exception()
    }

}