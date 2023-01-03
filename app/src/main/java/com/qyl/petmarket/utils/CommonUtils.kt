package com.qyl.petmarket.utils

/**
 * @author zhihu @ Zhihu Inc.
 * @since 01-03-2023
 */
object CommonUtils {

    var host = "59.45.228.160"

    fun convertUrl(url:String): String {
        val img = url.substringAfterLast("""\""")
        val newUrl = "http://${host}:5001/Images/${img}"
        return newUrl
    }

}