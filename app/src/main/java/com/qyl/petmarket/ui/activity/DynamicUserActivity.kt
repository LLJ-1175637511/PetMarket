package com.qyl.petmarket.ui.activity

import androidx.activity.viewModels
import com.qyl.petmarket.R
import com.qyl.petmarket.data.vm.BigPhotoVm
import com.qyl.petmarket.data.vm.DynamicSquareVM
import com.qyl.petmarket.data.vm.DynamicUserVM
import com.qyl.petmarket.databinding.ActivityDynamicMineBinding
import com.qyl.petmarket.net.NetActivity
import com.qyl.petmarket.ui.adapter.DynamicOtherRV

class DynamicUserActivity : NetActivity<ActivityDynamicMineBinding>() {

    override fun getLayoutId() = R.layout.activity_dynamic_mine

    private lateinit var adapter: DynamicOtherRV

    private val vm by viewModels<DynamicUserVM>()
    private val photoVm by viewModels<BigPhotoVm>()

    private var authorName: String? = null

    override fun init() {
        super.init()
        initSearchView()
        vm.queryUserDynamic(authorName)
    }

    private fun initSearchView() {
        authorName = intent.getStringExtra(TAG_AUTHOR)
        if (authorName == null) {
            mDataBinding.toolbar.toolbarBaseTitle.text = "我的动态"
        }else{
            mDataBinding.toolbar.toolbarBaseTitle.text = "${authorName} 的动态"
        }
        adapter = DynamicOtherRV(vm, photoVm)
        mDataBinding.recyclerView.adapter = adapter
        photoVm.bigUrl.observe(this) {
            if (it == null) {
                mDataBinding.flBG.hide()
                return@observe
            }
            mDataBinding.flBG.loadBigPhoto(it)
        }

        vm.searchList.observe(this) {
            adapter.update(it ?: emptyList())
        }
    }

    companion object {
        const val TAG_AUTHOR = "tag_author"
    }
}