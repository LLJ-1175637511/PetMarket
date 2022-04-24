package com.qyl.petmarket.ui.activity

import com.qyl.petmarket.R
import com.qyl.petmarket.databinding.ActivityDynamicSearchBinding
import com.qyl.petmarket.net.NetActivity

class SearchDynamicActivity:NetActivity<ActivityDynamicSearchBinding>() {

    override fun getLayoutId() = R.layout.activity_dynamic_search

    override fun init() {
        super.init()

    }

}