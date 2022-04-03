package com.lyc.epidemiccontrol.ui.activity

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.lyc.epidemiccontrol.R
import com.lyc.epidemiccontrol.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId() = R.layout.activity_main

    override fun init() {
        super.init()
        initNav()
    }

    private fun initNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        mDataBinding.navView.setupWithNavController(navController)
    }

}