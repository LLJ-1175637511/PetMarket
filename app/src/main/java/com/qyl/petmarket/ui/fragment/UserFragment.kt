package com.qyl.petmarket.ui.fragment

import com.bumptech.glide.Glide
import com.qyl.petmarket.R
import com.qyl.petmarket.data.bean.LoginBean
import com.qyl.petmarket.databinding.FragmentUserBinding
import com.qyl.petmarket.net.NetFragment
import com.qyl.petmarket.ui.activity.user.UserActivity

class UserFragment : NetFragment<FragmentUserBinding>() {

    override fun getLayoutId() = R.layout.fragment_user

    override fun initCreateView() {
        super.initCreateView()
        mDataBinding.toolbar.toolbarBaseTitle.text = "个人资料"

        mDataBinding.clUserInfo.setOnClickListener {
            startCommonActivity<UserActivity>()
        }
        getUserData {
            initUserData(it)
        }
    }

    private fun initUserData(data: LoginBean) {
        mDataBinding.toolbar.toolbarBaseTitle.text = "我的资料"
        mDataBinding.tvLike.text = "点赞记录：（${data.likedNums}）"
        mDataBinding.tvDynamic.text = "我的动态：（${data.dynamicNum}）"
        mDataBinding.tvUserName.text = data.userName
        val url = "http://47.110.231.180:8080${data.headPortrait}"
        Glide.with(this).load(url).into(mDataBinding.ivHead)
    }
}