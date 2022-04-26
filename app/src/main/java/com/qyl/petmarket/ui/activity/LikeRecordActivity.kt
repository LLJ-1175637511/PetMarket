package com.qyl.petmarket.ui.activity

import androidx.lifecycle.lifecycleScope
import com.qyl.petmarket.R
import com.qyl.petmarket.data.bean.DynamicBean
import com.qyl.petmarket.databinding.ActivityLikeRecordBinding
import com.qyl.petmarket.net.NetActivity
import com.qyl.petmarket.net.config.SysNetConfig
import com.qyl.petmarket.net.repository.SystemRepository
import com.qyl.petmarket.ui.adapter.LikeRV
import kotlinx.coroutines.launch

class LikeRecordActivity : NetActivity<ActivityLikeRecordBinding>() {

    override fun getLayoutId() = R.layout.activity_like_record

    private lateinit var adapter: LikeRV

    override fun init() {
        super.init()
        initMainView()
    }

    private fun initMainView() {
        mDataBinding.toolbar.toolbarBaseTitle.text = "点赞记录"
        lifecycleScope.launch {
            fastRequest<List<DynamicBean>> {
                SystemRepository.queryLikeRecordRequest(SysNetConfig.buildLikeRecord())
            }?.let {
                adapter = LikeRV(it)
                mDataBinding.recyclerView.adapter = adapter
            }
        }
    }
}