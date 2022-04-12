package com.qyl.petmarket.app

import android.app.Application
import com.qyl.petmarket.utils.ECLib

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ECLib.init(applicationContext)
    }
}