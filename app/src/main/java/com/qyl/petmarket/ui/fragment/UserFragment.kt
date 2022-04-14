package com.qyl.petmarket.ui.fragment

import android.content.Intent
import androidx.fragment.app.activityViewModels
import cn.leancloud.LCUser
import com.qyl.petmarket.R
import com.qyl.petmarket.data.vm.MainVM
import com.qyl.petmarket.databinding.FragmentUserBinding
import com.qyl.petmarket.ext.string
import com.qyl.petmarket.ui.activity.LoginActivity
import com.qyl.petmarket.ui.activity.ReviseUserActivity
import com.qyl.petmarket.ui.activity.UserActivity
import com.qyl.petmarket.utils.LCUtils

class UserFragment : BaseFragment<FragmentUserBinding>() {

    override fun getLayoutId() = R.layout.fragment_user

    override fun initCreateView() {
        super.initCreateView()
        val user = LCUser.getCurrentUser()
        mDataBinding.toolbar.toolbarBaseTitle.text = "个人资料"
        user?.let {
            mDataBinding.tvNum.text = it.username
            mDataBinding.tvUserName.text = it.get(LCUtils.LCUserAlias).string()
        }

        mDataBinding.tvReviseUserInfo.setOnClickListener {
            startCommonActivity<ReviseUserActivity>()
        }
        mDataBinding.clUserInfo.setOnClickListener {
            startCommonActivity<UserActivity>()
        }
    }

}