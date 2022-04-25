package com.qyl.petmarket.ui.fragment

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.qyl.petmarket.R
import com.qyl.petmarket.data.bean.PetBean
import com.qyl.petmarket.data.vm.PetVM
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

    private val vm by viewModels<PetVM>()

    private val launch =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { ar ->
            if (ar.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            vm.getPetInfo()
        }

    private lateinit var adapter:PetRV

    override fun initCreate() {
        super.initCreate()
        vm.getPetInfo()
    }

    override fun initCreateView() {
        super.initCreateView()
        mDataBinding.toolbar.toolbarBaseTitle.text = "宠   物"
        adapter = PetRV(vm)
        mDataBinding.recyclerView.adapter = adapter
        mDataBinding.clAddPet.setOnClickListener {
            launch.launch(Intent(requireContext(), AddPetActivity::class.java))
        }
        vm.petList.observe(this) {
            adapter.update(it ?: emptyList())
        }
    }

}