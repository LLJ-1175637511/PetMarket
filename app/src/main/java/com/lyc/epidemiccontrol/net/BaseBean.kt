package com.lyc.epidemiccontrol.net

import com.google.gson.JsonElement

/**
 * 所有借口结构基类
 */
data class BaseBean(
    val code: Int,
    val message: String,
    val data: JsonElement
)