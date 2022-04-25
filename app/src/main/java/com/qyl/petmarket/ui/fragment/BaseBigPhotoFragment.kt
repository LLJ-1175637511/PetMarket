package com.qyl.petmarket.ui.fragment

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import com.qyl.petmarket.data.vm.BigPhotoVm
import com.qyl.petmarket.net.NetFragment

abstract class BaseBigPhotoFragment<DB : ViewDataBinding>: NetFragment<DB>() {

    val photoVm by activityViewModels<BigPhotoVm>()

    override fun onPause() {
        super.onPause()
        photoVm.bigUrl.postValue(null)
    }

}