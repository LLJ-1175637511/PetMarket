package com.qyl.petmarket.ui.fragment

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.qyl.petmarket.R
import com.qyl.petmarket.data.bean.PetBean
import com.qyl.petmarket.databinding.FragmentMyPetBinding
import com.qyl.petmarket.net.NetFragment
import com.qyl.petmarket.net.repository.SystemRepository
import com.qyl.petmarket.ui.activity.AddPetActivity
import com.qyl.petmarket.ui.activity.user.ChooseHobbyActivity
import com.qyl.petmarket.ui.adapter.PetRV
import com.qyl.petmarket.utils.LogUtils
import kotlinx.coroutines.launch

class MyPetFragment : NetFragment<FragmentMyPetBinding>() {

    override fun getLayoutId() = R.layout.fragment_my_pet

    private val launch =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { ar ->
            if (ar.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            getPetInfo()
        }

    private val adapter by lazy { PetRV() }

    private fun getPetInfo() {
        lifecycleScope.launch {
            fastRequest<List<PetBean>> {
                SystemRepository.findPetRequest()
            }?.let {
                LogUtils.d("MyPetFragment","MyPetFragment:${it.toString()}")
                adapter.update(it)
            }
        }
    }

    override fun initCreateView() {
        super.initCreateView()
        mDataBinding.toolbar.toolbarBaseTitle.text = "我的宠物"
        mDataBinding.recyclerView.adapter = adapter
        mDataBinding.clAddPet.setOnClickListener {
            launch.launch(Intent(requireContext(),AddPetActivity::class.java))
        }
        getPetInfo()
    }

}