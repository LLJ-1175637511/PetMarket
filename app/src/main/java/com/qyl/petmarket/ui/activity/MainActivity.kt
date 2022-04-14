package com.qyl.petmarket.ui.activity

import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.qyl.petmarket.R
import com.qyl.petmarket.data.bean.LoginBean
import com.qyl.petmarket.data.vm.MainVM
import com.qyl.petmarket.databinding.ActivityMainBinding
import com.qyl.petmarket.ui.activity.user.LoginActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId() = R.layout.activity_main

    private val vm by viewModels<MainVM>()

    override fun init() {
        super.init()
        initNav()
        initData()
    }

    private fun initData() {
        intent.getParcelableExtra<LoginBean>(LoginActivity.userInfo)?.let {
            vm.userInfo.postValue(it)
        }
    }

    private fun initNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        mDataBinding.navView.setupWithNavController(navController)
    }

}