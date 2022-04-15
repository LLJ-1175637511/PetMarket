package com.qyl.petmarket.ui.activity

import com.qyl.petmarket.R
import com.qyl.petmarket.databinding.ActivityAddPetBinding

class AddPetActivity:BaseActivity<ActivityAddPetBinding>() {

    override fun getLayoutId() = R.layout.activity_add_pet

    override fun init() {
        super.init()

        mDataBinding.toolbar.toolbarBaseTitle.text = "添加宠物"

    }

}