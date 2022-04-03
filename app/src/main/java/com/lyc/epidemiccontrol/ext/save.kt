package com.lyc.epidemiccontrol.ext

import android.content.SharedPreferences

fun SharedPreferences.save(block: SharedPreferences.Editor.() -> Unit): Boolean {
    val edit = edit()
    block(edit)
    return edit.commit()
}