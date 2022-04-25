package com.qyl.petmarket.ui.fragment

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.qyl.petmarket.R
import com.qyl.petmarket.data.bean.DynamicBean
import com.qyl.petmarket.data.bean.LoginBean
import com.qyl.petmarket.data.vm.DynamicSquareVM
import com.qyl.petmarket.databinding.FragmentPetDynamicBinding
import com.qyl.petmarket.databinding.FragmentUserBinding
import com.qyl.petmarket.net.NetFragment
import com.qyl.petmarket.ui.activity.user.UserActivity
import com.qyl.petmarket.ui.adapter.DynamicRV
import com.qyl.petmarket.utils.LogUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DynamicFragment(private val dynamicType: DynamicType) :
    NetFragment<FragmentPetDynamicBinding>() {

    override fun getLayoutId() = R.layout.fragment_pet_dynamic

    private val vm by activityViewModels<DynamicSquareVM>()

    private lateinit var adapter: DynamicRV

    override fun initCreate() {
        super.initCreate()
        vm.queryData(dynamicType)
    }

    override fun initCreateView() {
        super.initCreateView()
        adapter = DynamicRV(vm)
        mDataBinding.recyclerView.adapter = adapter
        when (dynamicType) {
            DynamicType.日常 -> {
                vm.normalList.observe(this) {
                    adapter.update(it ?: emptyList())
                }
            }
            DynamicType.好物 -> {
                vm.goodThingList.observe(this) {
                    adapter.update(it ?: emptyList())
                }
            }
            DynamicType.科普 -> {
                vm.scienceList.observe(this) {
                    adapter.update(it ?: emptyList())
                }
            }
        }
        vm.freshState.observe(this){
            if (it){
                lifecycleScope.launch {
                    delay(200)
                    mDataBinding.refresh.isRefreshing = false
                }
            }
        }
        mDataBinding.refresh.setOnRefreshListener {
            vm.queryData(dynamicType)
        }
    }

    enum class DynamicType {
        日常, 科普, 好物
    }
}