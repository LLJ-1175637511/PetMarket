package com.qyl.petmarket.utils

import android.widget.Toast

object ToastUtils {

    fun toastShort(msg:String){
        Toast.makeText(ECLib.getC(),msg,Toast.LENGTH_SHORT).show()
    }

    fun toastLong(msg:String){
        Toast.makeText(ECLib.getC(),msg,Toast.LENGTH_LONG).show()
    }
}

fun Int.addZero(): String = if (this < 10) "0$this" else this.toString()