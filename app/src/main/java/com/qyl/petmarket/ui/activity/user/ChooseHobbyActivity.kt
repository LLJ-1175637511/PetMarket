package com.qyl.petmarket.ui.activity.user

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import cn.leancloud.LCObject
import cn.leancloud.LCUser
import com.qyl.petmarket.R
import com.qyl.petmarket.databinding.ActivityChooseHobbyBinding
import com.qyl.petmarket.ext.save
import com.qyl.petmarket.ui.activity.BaseActivity
import com.qyl.petmarket.ui.activity.MainActivity
import com.qyl.petmarket.utils.Const
import com.qyl.petmarket.utils.ECLib
import com.qyl.petmarket.utils.LCUtils
import com.qyl.petmarket.utils.ToastUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class ChooseHobbyActivity : BaseActivity<ActivityChooseHobbyBinding>() {

    override fun getLayoutId() = R.layout.activity_choose_hobby

    override fun init() {
        super.init()
        initClick()
    }

    private fun initClick() {
        mDataBinding.apply {
            birds.setOnClickListener {
                cbBirds.visibility = if (cbBirds.isVisible) View.INVISIBLE
                else View.VISIBLE
            }
            fish.setOnClickListener {
                cbfish.visibility = if (cbfish.isVisible) View.INVISIBLE
                else View.VISIBLE
            }
            cat.setOnClickListener {
                cbCat.visibility = if (cbCat.isVisible) View.INVISIBLE
                else View.VISIBLE
            }
            dog.setOnClickListener {
                cbDog.visibility = if (cbDog.isVisible) View.INVISIBLE
                else View.VISIBLE
            }
            mouse.setOnClickListener {
                cbmouse.visibility = if (cbmouse.isVisible) View.INVISIBLE
                else View.VISIBLE
            }
            pig.setOnClickListener {
                cbpig.visibility = if (cbpig.isVisible) View.INVISIBLE
                else View.VISIBLE
            }
            tvToMain.setOnClickListener {
                checkChoose{
                    ECLib.getSP(Const.SPUser).save {
                        putString(Const.SPHadChooseHobby, "init")
                    }
                    startActivityAndFinish<MainActivity>()
                }
            }
            tvSureChoose.setOnClickListener {
                checkChoose{
                    finish()
                }
            }
        }
        val tag = intent.getStringExtra(TAG_IS_CHANGE)
        if (tag == null){
            mDataBinding.tvToMain.visibility = View.VISIBLE
        }else{
            mDataBinding.tvSureChoose.visibility = View.VISIBLE
        }
    }

    private fun checkChoose(block:()->Unit) {
        kotlin.runCatching {
            lifecycleScope.launch {
                val sb = StringBuilder()
                if (mDataBinding.cbBirds.isVisible) sb.append("鸟_")
                if (mDataBinding.cbfish.isVisible) sb.append("鱼_")
                if (mDataBinding.cbCat.isVisible) sb.append("猫_")
                if (mDataBinding.cbDog.isVisible) sb.append("狗_")
                if (mDataBinding.cbmouse.isVisible) sb.append("鼠_")
                if (mDataBinding.cbpig.isVisible) sb.append("猪_")
                var str = sb.toString()
                if (str.last() == '_') str = str.substring(str.indices)
                val user = LCUser.getCurrentUser()
                user.put(LCUtils.LCUserHobby, str)
                user.saveInBackground().subscribe(object : Observer<LCObject>{
                    override fun onNext(t: LCObject) {
                        block()
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.toastShort("选择失败：${e.message}")
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onComplete() {
                    }

                })
            }
        }
    }

    companion object{
        const val TAG_IS_CHANGE = "tag_is_change"
    }
}