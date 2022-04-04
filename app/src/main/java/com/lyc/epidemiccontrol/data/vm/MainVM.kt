package com.lyc.epidemiccontrol.data.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lyc.epidemiccontrol.data.bean.LoginBean

class MainVM:ViewModel() {

    val userInfo = MutableLiveData<LoginBean>()



}