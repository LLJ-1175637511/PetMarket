package com.qyl.petmarket.ui.activity

import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.qyl.petmarket.R
import com.qyl.petmarket.data.vm.BigPhotoVm
import com.qyl.petmarket.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId() = R.layout.activity_main

    val vm by viewModels<BigPhotoVm>()

    override fun init() {
        super.init()
        initNav()
        initPhotoBG()
    }

    private fun initPhotoBG() {
        vm.bigUrl.observe(this){
            if (it == null) {
                mDataBinding.flBG.hide()
                return@observe
            }
            mDataBinding.flBG.loadBigPhoto(it)
        }
    }

    private fun initNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        mDataBinding.navView.setupWithNavController(navController)
    }

}