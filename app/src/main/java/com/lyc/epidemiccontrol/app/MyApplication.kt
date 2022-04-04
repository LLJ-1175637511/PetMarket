package com.lyc.epidemiccontrol.app

import android.app.Application
import com.lyc.epidemiccontrol.data.bean.LoginBean
import com.lyc.epidemiccontrol.utils.ECLib

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ECLib.init(applicationContext)
    }
}