package com.qyl.petmarket.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

@SuppressLint("StaticFieldLeak")
object ECLib {

    private lateinit var context: Context

    private var isInit = false

    @JvmStatic
    fun init(context:Context){
        if (isInit){
            throw IllegalArgumentException("请勿多次初始化")
        }
        this.context = context.applicationContext
    }

    fun getC() = context

    const val TAG = "EpidemicControl"

    fun getSP(key: String): SharedPreferences =
        context.getSharedPreferences(key, Context.MODE_PRIVATE)

}