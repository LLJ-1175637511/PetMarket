package com.qyl.petmarket.ui.fragment

import com.qyl.petmarket.R
import com.qyl.petmarket.databinding.FragmentMyPetBinding
import com.qyl.petmarket.ui.activity.AddPetActivity

class MyPetFragment : BaseFragment<FragmentMyPetBinding>() {

    override fun getLayoutId() = R.layout.fragment_my_pet

    override fun initCreate() {
        super.initCreate()
    }

    override fun onResume() {
        super.onResume()
        getPetInfo()
    }

    private fun getPetInfo() {

    }

    override fun initCreateView() {
        super.initCreateView()
        mDataBinding.clAddPet.setOnClickListener {
            startCommonActivity<AddPetActivity>()
        }
    }

}