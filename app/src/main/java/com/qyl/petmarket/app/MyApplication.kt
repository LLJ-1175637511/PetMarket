package com.qyl.petmarket.app

import android.app.Application
import cn.leancloud.LeanCloud
import com.qyl.petmarket.utils.ECLib

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ECLib.init(applicationContext)
        LeanCloud.initialize(this, "9SldQj3XmgnRx73LFxsssQmq-gzGzoHsz", "fLOEDGaGjnaJPLtsAWKaNxgv" , "https://9sldqj3x.lc-cn-n1-shared.com")
    }
}