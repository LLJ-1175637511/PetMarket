package com.qyl.petmarket.ui.fragment

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.qyl.petmarket.R
import com.qyl.petmarket.data.vm.PetVM
import com.qyl.petmarket.databinding.FragmentMyPetBinding
import com.qyl.petmarket.ui.activity.AddPetActivity
import com.qyl.petmarket.ui.adapter.PetRV

class MyPetFragment : BaseBigPhotoFragment<FragmentMyPetBinding>() {

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
        adapter = PetRV(vm){
            photoVm.bigUrl.postValue(it)
        }
        mDataBinding.recyclerView.adapter = adapter
        mDataBinding.clAddPet.setOnClickListener {
            launch.launch(Intent(requireContext(), AddPetActivity::class.java))
        }
        vm.petList.observe(this) {
            adapter.update(it ?: emptyList())
        }
    }

}