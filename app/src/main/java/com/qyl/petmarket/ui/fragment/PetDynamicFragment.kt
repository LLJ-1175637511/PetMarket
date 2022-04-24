package com.qyl.petmarket.ui.fragment

import com.bumptech.glide.Glide
import com.qyl.petmarket.R
import com.qyl.petmarket.data.bean.LoginBean
import com.qyl.petmarket.databinding.FragmentPetDynamicBinding
import com.qyl.petmarket.databinding.FragmentUserBinding
import com.qyl.petmarket.net.NetFragment
import com.qyl.petmarket.ui.activity.user.UserActivity
import com.qyl.petmarket.ui.adapter.DynamicRV

class PetDynamicFragment(val dynamicType:DynamicType) : NetFragment<FragmentPetDynamicBinding>() {

    override fun getLayoutId() = R.layout.fragment_pet_dynamic

    private lateinit var adapter:DynamicRV

    override fun initCreateView() {
        super.initCreateView()

        getUserData {
            initUserData(it)
        }
    }

    private fun initUserData(data: LoginBean) {

    }

    enum class DynamicType{
        日常,科普,好物
    }
}