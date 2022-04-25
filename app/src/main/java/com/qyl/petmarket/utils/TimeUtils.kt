package com.qyl.petmarket.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

/***
 * v表示：
 * b表示-
 */
enum class TimeEnum {
    Y年M月D日,//2020年02月16日
    YYMMDD,//20200216
    YYbMMbDD,//2020-02-16
    YYbMMbDDHHvMMvSS, //2020-02-16 10:10:10
    THIS
}

@SuppressLint("SimpleDateFormat")
fun Long.convertTime(timeEnum: TimeEnum): String {
    val type = when (timeEnum) {
        TimeEnum.YYMMDD -> {
            "yyyyMMdd"
        }
        TimeEnum.Y年M月D日 -> {
            "yyyy年MM月dd日"
        }
        TimeEnum.YYbMMbDD -> {
            "yyyy-MM-dd"
        }
        TimeEnum.YYbMMbDDHHvMMvSS -> {
            "yyyy-MM-dd hh-mm-ss"
        }
        TimeEnum.THIS -> {
            "yyyy-MM-dd hh:mm:ss"
        }
    }
    return SimpleDateFormat(type).format(this)
}


/*
格林尼治时间转换
 */
@SuppressLint("SimpleDateFormat")
fun String.convertGeLinTime(): String = this.substring(0..9)
fun String.convertGeLinAllTime(): String = this.substring(10..(this.lastIndex))

fun String.convertLongTime(timeEnum: TimeEnum): Long {
    val type = when (timeEnum) {
        TimeEnum.YYMMDD -> {
            "yyyyMMdd"
        }
        TimeEnum.Y年M月D日 -> {
            "yyyy年MM月dd日"
        }
        TimeEnum.YYbMMbDD -> {
            "yyyy-MM-dd"
        }
        TimeEnum.YYbMMbDDHHvMMvSS -> {
            "yyyy-MM-dd hh-mm-ss"
        }
        TimeEnum.THIS -> {
            "yyyy-MM-dd hh:mm:ss"
        }
    }
    return SimpleDateFormat(type).parse(this).time
}

fun Int.addZero(): String = if (this < 10) "0$this" else this.toString()