package com.qyl.petmarket.ui.activity.user

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import cn.leancloud.LCObject
import cn.leancloud.LCUser
import com.qyl.petmarket.R
import com.qyl.petmarket.databinding.ActivityChooseHobbyBinding
import com.qyl.petmarket.ext.save
import com.qyl.petmarket.net.NetActivity
import com.qyl.petmarket.net.repository.SystemRepository
import com.qyl.petmarket.ui.activity.BaseActivity
import com.qyl.petmarket.ui.activity.MainActivity
import com.qyl.petmarket.utils.*
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class ChooseHobbyActivity : NetActivity<ActivityChooseHobbyBinding>() {

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
                    setResult(RESULT_OK,Intent().putExtra(TAG_NEW_PREFERENCE,it))
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

    private fun checkChoose(block:(t:String)->Unit) {
        kotlin.runCatching {
            lifecycleScope.launch {
                val sb = StringBuilder()
                if (mDataBinding.cbBirds.isVisible) sb.append("鸟 ")
                if (mDataBinding.cbfish.isVisible) sb.append("鱼 ")
                if (mDataBinding.cbCat.isVisible) sb.append("猫 ")
                if (mDataBinding.cbDog.isVisible) sb.append("狗 ")
                if (mDataBinding.cbmouse.isVisible) sb.append("鼠 ")
                if (mDataBinding.cbpig.isVisible) sb.append("猪 ")
                var str = sb.toString()
                if (str.last() == ' ') str = str.substring(str.indices)
                fastRequest<Boolean> {
                    SystemRepository.preferenceRequest(str)
                }?.let {
                    block(str)
                }
            }
        }
    }
    companion object{
        const val TAG_IS_CHANGE = "tag_is_change"
        const val TAG_NEW_PREFERENCE = "tag_new_preference"
    }
}