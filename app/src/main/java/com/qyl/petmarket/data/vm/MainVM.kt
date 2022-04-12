package com.qyl.petmarket.data.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qyl.petmarket.data.bean.LoginBean

class MainVM:ViewModel() {

    val userInfo = MutableLiveData<LoginBean>()



}