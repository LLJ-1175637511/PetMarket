package com.qyl.petmarket.ui.activity.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qyl.petmarket.R
import com.qyl.petmarket.databinding.ActivityNetBinding
import com.qyl.petmarket.ext.string
import com.qyl.petmarket.ui.activity.BaseActivity
import com.qyl.petmarket.utils.CommonUtils

class NetActivity : BaseActivity<ActivityNetBinding>() {

    override fun getLayoutId() = R.layout.activity_net

    override fun init() {
        super.init()
        mDataBinding.button.setOnClickListener {
            CommonUtils.host = mDataBinding.host.text.toString()
            finish()
        }
    }

}