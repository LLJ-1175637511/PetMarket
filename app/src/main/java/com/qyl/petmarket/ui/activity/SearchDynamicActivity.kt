package com.qyl.petmarket.ui.activity

import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.qyl.petmarket.R
import com.qyl.petmarket.data.vm.BigPhotoVm
import com.qyl.petmarket.data.vm.DynamicSquareVM
import com.qyl.petmarket.databinding.ActivityDynamicSearchBinding
import com.qyl.petmarket.net.NetActivity
import com.qyl.petmarket.ui.adapter.DynamicRV
import com.qyl.petmarket.ui.adapter.DynamicSearchRV
import com.qyl.petmarket.utils.ToastUtils

class SearchDynamicActivity : NetActivity<ActivityDynamicSearchBinding>() {

    override fun getLayoutId() = R.layout.activity_dynamic_search

    private lateinit var adapter: DynamicSearchRV

    private val vm by viewModels<DynamicSquareVM>()
    private val photoVm by viewModels<BigPhotoVm>()

    override fun init() {
        super.init()
        initSearchView()
    }

    override fun onPause() {
        super.onPause()
        photoVm.bigUrl.postValue(null)
    }

    private fun initSearchView() {
        adapter = DynamicSearchRV(vm,photoVm)
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