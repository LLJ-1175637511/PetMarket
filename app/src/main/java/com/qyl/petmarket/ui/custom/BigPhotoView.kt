package com.qyl.petmarket.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.qyl.petmarket.R
import com.qyl.petmarket.utils.LogUtils


class BigPhotoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 1
) : FrameLayout(context, attrs, defStyleAttr) {

    private var bigPhoto: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_big_photo, this, true)
        bigPhoto = findViewById(R.id.ivBigPhoto)
        this.visibility = GONE
        this.setOnClickListener {
            hide()
        }
    }

    fun loadBigPhoto(url: String) {
        LogUtils.d("BigPhotoView", "loading")
        this.visibility = View.VISIBLE
        Glide.with(context).load(url).into(bigPhoto)
    }

    fun hide() {
        this.visibility = View.GONE
    }

}