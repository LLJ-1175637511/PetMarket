package com.qyl.petmarket.ui.activity.user

import cn.leancloud.LCObject
import cn.leancloud.LCUser
import com.qyl.petmarket.R
import com.qyl.petmarket.databinding.ActivityUserReviseBinding
import com.qyl.petmarket.ext.save
import com.qyl.petmarket.ui.activity.BaseActivity
import com.qyl.petmarket.utils.Const
import com.qyl.petmarket.utils.ECLib
import com.qyl.petmarket.utils.LCUtils
import com.qyl.petmarket.utils.ToastUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class ReviseUserActivity: BaseActivity<ActivityUserReviseBinding>() {

    override fun getLayoutId() = R.layout.activity_user_revise

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
        mDataBinding.toolbar.toolbarBaseTitle.text = "用户信息修改"
        val user = LCUser.getCurrentUser()
        mDataBinding.username.setText(user.getString(LCUtils.LCUserAlias))
        mDataBinding.address.setText(user.getString(LCUtils.LCUserAddress))
        mDataBinding.age.setText(user.getString(LCUtils.LCUserAge))
        if (user.getString(LCUtils.LCUserSex) == "男"){
            mDataBinding.radioGroup.check(R.id.rb1)
        }else{
            mDataBinding.radioGroup.check(R.id.rb2)
        }
        mDataBinding.userNumber.setText(user.username)
        val usp = ECLib.getSP(Const.SPUser)
        mDataBinding.password.setText(usp.getString(Const.SPUserPwd,""))
        mDataBinding.confirmPassword.setText(usp.getString(Const.SPUserPwd,""))
        mDataBinding.email.setText(user.email)
        mDataBinding.phone.setText(user.mobilePhoneNumber)

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

                val age = mDataBinding.age.text.toString()
                if (address.isEmpty()) toastErr(mDataBinding.age.hint.toString())

                val sex = if (mDataBinding.radioGroup.checkedRadioButtonId == R.id.rb1){
                    mDataBinding.rb1.text.toString()
                }else{
                    mDataBinding.rb2.text.toString()
                }

                register(username, userNumber, password, email, phone, address, sex, age)

            }
        }
    }


    private fun register(
        username: String,
        userNumber: String,
        password: String,
        email: String,
        phone: String,
        address: String,
        sex: String,
        age: String
    ) {
        val user = LCUser.getCurrentUser()

        user.username = userNumber
        user.password = password

        user.email = email
        user.mobilePhoneNumber = phone

        user.put(LCUtils.LCUserAddress, address)
        user.put(LCUtils.LCUserAlias, username)
        user.put(LCUtils.LCUserSex, sex)
        user.put(LCUtils.LCUserAge, age)

        user.saveInBackground().subscribe(object : Observer<LCObject> {
            override fun onSubscribe(disposable: Disposable) {}
            override fun onNext(user: LCObject) {
                ToastUtils.toastShort("修改成功 请重新登录")
                ECLib.getSP(Const.SPUser).save {
                    putString(Const.SPUserPwd, password)
                    putString(Const.SPUserName, userNumber)
                }
                startActivityAndFinish<LoginActivity>()
            }

            override fun onError(throwable: Throwable) {
                // 注册失败（通常是因为用户名已被使用）
                ToastUtils.toastShort("修改失败 ${throwable.message}")
            }

            override fun onComplete() {}
        })
    }

    private fun toastErr(s: String) {
        ToastUtils.toastShort("${s}不能为空")
        throw Exception()
    }

}