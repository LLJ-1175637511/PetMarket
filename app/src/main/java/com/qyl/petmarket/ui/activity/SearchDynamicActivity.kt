package com.qyl.petmarket.ui.activity

import androidx.activity.viewModels
import com.qyl.petmarket.R
import com.qyl.petmarket.data.vm.BigPhotoVm
import com.qyl.petmarket.data.vm.DynamicUserVM
import com.qyl.petmarket.databinding.ActivityDynamicSearchBinding
import com.qyl.petmarket.net.NetActivity
import com.qyl.petmarket.ui.adapter.DynamicOtherRV
import com.qyl.petmarket.utils.ToastUtils

class SearchDynamicActivity : NetActivity<ActivityDynamicSearchBinding>() {

    override fun getLayoutId() = R.layout.activity_dynamic_search

    private lateinit var adapter: DynamicOtherRV

    private val vm by viewModels<DynamicUserVM>()
    private val photoVm by viewModels<BigPhotoVm>()

    override fun init() {
        super.init()
        initSearchView()
    }

    private fun initSearchView() {
        adapter = DynamicOtherRV(vm,photoVm)
        mDataBinding.recyclerView.adapter = adapter
        photoVm.bigUrl.observe(this){
            if (it == null) {
                mDataBinding.flBG.hide()
                return@observe
            }
            mDataBinding.flBG.loadBigPhoto(it)
        }
        mDataBinding.tvFilter.setOnClickListener {
            val content = mDataBinding.etFilterContent.text.trim().toString()
            if (content.isNotEmpty()){
                hideKeyBoard()
                adapter.setContent(content)
                vm.querySearchData(content)
            }else{
                ToastUtils.toastShort("内容不能为空")
            }
        }
        vm.searchList.observe(this){
            adapter.update(it ?: emptyList())
        }
    }

}