package com.lyc.epidemiccontrol.ui.activity

import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.data.bean.LoginBean
import com.lyc.epidemiccontrol.data.vm.MainVM
import com.lyc.epidemiccontrol.databinding.ActivityMainBinding

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