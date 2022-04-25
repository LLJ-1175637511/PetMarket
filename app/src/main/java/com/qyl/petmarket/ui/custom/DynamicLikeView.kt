package com.qyl.petmarket.ui.custom

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.qyl.petmarket.R
import com.qyl.petmarket.utils.LogUtils


class DynamicLikeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 1
) : LinearLayout(context, attrs, defStyleAttr) {

    var mIsLiked = false
    var mLikeCount = 0

    private var mLiked: ImageView
    private var mCount: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_like_dynamic, this, true)
        mLiked = findViewById(R.id.ivLikeCustom)
        mCount = findViewById(R.id.tvLikeCountCustom)
    }

    fun clicked() {
        if (mIsLiked) {
            mLikeCount--
        } else {
            mLikeCount++
        }
        mIsLiked = !mIsLiked
        updateLikeData(mIsLiked, mLikeCount)
        if (mIsLiked) startAnimate()
    }

    fun init(isLiked: Boolean, count: Int) {
        mIsLiked = isLiked
        mLikeCount = count
        updateLikeData(isLiked, count)
    }

    private fun updateLikeData(isLiked: Boolean, count: Int) {
        if (isLiked) {
            mLiked.setImageResource(R.drawable.ic_baseline_thumb_up_24)
        } else {
            mLiked.setImageResource(R.drawable.ic_baseline_thumb_up_no_24)
        }
        mCount.text = count.toString()
    }

    private fun startAnimate() {
        ValueAnimator.ofFloat(1f, 1.3f,1f).apply {
            duration = 500
            LogUtils.d("DynamicLikeView",mLiked.translationY.toString())
            addUpdateListener {
                val v = it.animatedValue as Float
                mLiked.scaleX = v
                mLiked.scaleY = v

            }
        }.start()
    }

}