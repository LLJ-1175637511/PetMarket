package com.qyl.petmarket.ui.activity.user

import android.content.Intent
import cn.leancloud.LCUser
import com.qyl.petmarket.R
import com.qyl.petmarket.databinding.ActivityUserBinding
import com.qyl.petmarket.ext.string
import com.qyl.petmarket.ui.activity.BaseActivity
import com.qyl.petmarket.utils.Const
import com.qyl.petmarket.utils.LCUtils

class UserActivity : BaseActivity<ActivityUserBinding>() {

    override fun getLayoutId() = R.layout.activity_user

    override fun onResume() {
        super.onResume()
        val user = LCUser.getCurrentUser()

        user?.let {
            mDataBinding.toolbar.toolbarBaseTitle.text = "我的资料"
            mDataBinding.tvEmail.text = "邮箱：${it.email}"
            mDataBinding.tvPhone.text = "电话：${it.mobilePhoneNumber}"
            mDataBinding.tvInfo.text =
                "${it.getString(LCUtils.LCUserSex)}    ${it.get(LCUtils.LCUserAge)}   |   学生   |   ${
                    it.getString(
                        LCUtils.LCUserAddress
                    )
                }"
            mDataBinding.tvNum.text = it.username
            mDataBinding.tvUserName.text = it.get(LCUtils.LCUserAlias).string()
            val hobby = it.getString(LCUtils.LCUserHobby)
            mDataBinding.tvHobby.append(hobby.replace(","," "))
            mDataBinding.tvHobby.text = "喜好：${it.getString(LCUtils.LCUserHobby).replace('_',' ')}"
        }
    }

    override fun init() {
        super.init()
        mDataBinding.tvChangeHobby.setOnClickListener {
            startActivity(Intent(this,ChooseHobbyActivity::class.java)
                .putExtra(ChooseHobbyActivity.TAG_IS_CHANGE,"isChange")
            )
        }
        mDataBinding.tvQuit.setOnClickListener {
            startActivityAndFinish<LoginActivity>()
        }
    }

}