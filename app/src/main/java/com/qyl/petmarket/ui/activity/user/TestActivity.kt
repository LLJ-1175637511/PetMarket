package com.qyl.petmarket.ui.activity.user

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import androidx.core.view.animation.PathInterpolatorCompat
import com.facebook.rebound.SimpleSpringListener
import com.facebook.rebound.Spring
import com.facebook.rebound.SpringConfig
import com.facebook.rebound.SpringSystem
import com.google.android.material.animation.AnimatorSetCompat
import com.qyl.petmarket.R
import com.qyl.petmarket.databinding.ActivityTestBinding
import com.qyl.petmarket.ui.activity.BaseActivity


class TestActivity : BaseActivity<ActivityTestBinding>() {

    override fun init() {
        super.init()
        mDataBinding.button.setOnClickListener {
            moreImgAnimateToShareImage()
        }
        mDataBinding.btWechat.setOnClickListener {
            startWeChatAnimate()
        }

    }

    private fun startWeChatAnimate() {
        //微信icon抖动动画
        val weChatAnimator = ValueAnimator.ofFloat(1f, 0f).setDuration(300)
        fun startWeChatAnimate() = SpringSystem.create().createSpring().apply {
            springConfig = SpringConfig.fromOrigamiTensionAndFriction(400.0, 18.0)
            endValue = 1.0
            addListener(object : SimpleSpringListener() {
                override fun onSpringUpdate(spring: Spring) {
                    val springValue = spring.currentValue.toFloat()
                    mDataBinding.toolbarWeChat.scaleX = springValue
                    mDataBinding.toolbarWeChat.scaleY = springValue
                }
            })
        }
        weChatAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            mDataBinding.toolbarMore.alpha = value
            mDataBinding.toolbarMore.scaleX = value
            mDataBinding.toolbarMore.scaleY = value
            if (value == 0f) {
                mDataBinding.toolbarMore.visibility = View.GONE
                mDataBinding.toolbarWeChat.visibility = View.VISIBLE
                startWeChatAnimate()
            }
        }
        weChatAnimator.start()
    }

    private fun moreImgAnimateToShareImage() {

        val pathInterpolator = PathInterpolatorCompat.create(.42f, 0f, .58f, 1f)
        val animatorAlpha = ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener {
                mDataBinding.toolbarLLImgTitle.alpha = it.animatedValue as Float
            }
            interpolator = pathInterpolator
            duration = 300
        }
        val animatorY = ValueAnimator.ofFloat(-50f, 0f).apply {
            interpolator = pathInterpolator
            duration = 300
            addUpdateListener {
                val v = it.animatedValue as Float
                mDataBinding.toolbarLLImgTitle.scrollY = v.toInt()
            }
        }
        AnimatorSet().apply {
            play(animatorY).with(animatorAlpha).after(200)
            start()
        }
    }

    override fun getLayoutId() = R.layout.activity_test
}