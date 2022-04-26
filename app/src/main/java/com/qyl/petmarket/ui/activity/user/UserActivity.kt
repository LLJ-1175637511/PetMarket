package com.qyl.petmarket.ui.activity.user

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.qyl.petmarket.R
import com.qyl.petmarket.data.bean.LoginBean
import com.qyl.petmarket.databinding.ActivityUserMineBinding
import com.qyl.petmarket.net.NetActivity

class UserActivity : NetActivity<ActivityUserMineBinding>() {

    override fun getLayoutId() = R.layout.activity_user_mine

    private val launch =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { ar ->
            if (ar.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            ar.data?.getStringExtra(ChooseHobbyActivity.TAG_NEW_PREFERENCE)?.let {
                mDataBinding.tvHobby.text = "喜好：$it"
            }
        }

    override fun init() {
        super.init()
        mDataBinding.tvChangeHobby.setOnClickListener {
            launch.launch(
                Intent(this, ChooseHobbyActivity::class.java)
                    .putExtra(ChooseHobbyActivity.TAG_IS_CHANGE, "isChange")
            )
        }
        mDataBinding.tvQuit.setOnClickListener {
            startActivityAndFinish<LoginActivity>()
        }
        getUserData {
            initUserData(it)
        }
    }

    private fun initUserData(data: LoginBean) {
        mDataBinding.toolbar.toolbarBaseTitle.text = "我的资料"
        mDataBinding.tvEmail.text = "邮箱：${data.eamil}"
        mDataBinding.tvPhone.text = "电话：${data.telephone}"
        mDataBinding.tvInfo.text = "${data.gender}    |   学生"
        mDataBinding.tvUserName.text = data.userName
        var hobby = ""
        data.preference.forEach {
            hobby += "$it "
        }
        mDataBinding.tvHobby.text = "喜好：$hobby"
        val url = "http://47.110.231.180:8080${data.headPortrait}"
        Glide.with(this).load(url).into(mDataBinding.ivHead)
    }

}