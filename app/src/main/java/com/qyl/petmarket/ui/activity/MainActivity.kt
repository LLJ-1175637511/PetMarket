package com.qyl.petmarket.ui.activity

import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.qyl.petmarket.R
import com.qyl.petmarket.data.bean.LoginBean
import com.qyl.petmarket.data.vm.DynamicSquareVM
import com.qyl.petmarket.data.vm.MainVM
import com.qyl.petmarket.databinding.ActivityMainBinding
import com.qyl.petmarket.ui.activity.user.LoginActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId() = R.layout.activity_main

    private val vm by viewModels<DynamicSquareVM>()

    override fun init() {
        super.init()
        initNav()
        initPhotoBG()
    }

    private fun initPhotoBG() {
        vm.bigUrl.observe(this){
            if (it == null){
                mDataBinding.flBG.visibility = View.GONE
            }else{
                mDataBinding.flBG.visibility = View.VISIBLE
                Glide.with(this).load(it).into(mDataBinding.ivBigPhoto)
            }
        }
        mDataBinding.flBG.setOnClickListener {
            vm.bigUrl.postValue(null)
        }
    }

    private fun initNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        mDataBinding.navView.setupWithNavController(navController)
    }

}