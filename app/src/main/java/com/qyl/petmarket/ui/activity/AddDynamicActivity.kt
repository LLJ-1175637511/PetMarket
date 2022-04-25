package com.qyl.petmarket.ui.activity

import android.net.Uri
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.qyl.petmarket.R
import com.qyl.petmarket.databinding.ActivityDynamicAddBinding
import com.qyl.petmarket.net.config.SysNetConfig
import com.qyl.petmarket.net.repository.SystemRepository
import com.qyl.petmarket.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddDynamicActivity : BaseAddPhotoActivity<ActivityDynamicAddBinding>() {

    override fun getLayoutId() = R.layout.activity_dynamic_add

    private var pteKind: String? = null

    private val petKindList = listOf("鸟", "鱼", "猫", "狗", "鼠", "猪")

    private lateinit var petKindAdapter: ArrayAdapter<String>

    override fun init() {
        super.init()
        mDataBinding.ivBack.setOnClickListener {
            finish()
        }
        mDataBinding.tvAddDynamic.setOnClickListener {
            addDynamic()
        }
        mDataBinding.ivDynamicPhoto.setOnClickListener {
            choosePhoto()
        }
        buildLaunch {
            Glide.with(this).load(uri).into(mDataBinding.ivDynamicPhoto)
        }
        mDataBinding.tvSetPetKind.setOnClickListener {
            mDataBinding.listView.visibility = View.VISIBLE
        }
        petKindAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, petKindList)
        mDataBinding.listView.adapter = petKindAdapter
        mDataBinding.listView.setOnItemClickListener { adapterView, view, i, l ->
            mDataBinding.tvSetPetKind.text = petKindList[i]
            mDataBinding.listView.visibility = View.INVISIBLE
        }
    }

    private fun addDynamic() {
        val content = mDataBinding.etContent.text.trim().toString()
        val type = when (mDataBinding.radioGroup.checkedRadioButtonId) {
            R.id.rb1 -> mDataBinding.rb1.text.toString()
            R.id.rb2 -> mDataBinding.rb2.text.toString()
            R.id.rb3 -> mDataBinding.rb3.text.toString()
            else -> ""
        }
        lifecycleScope.launch {
            val map = SysNetConfig.buildAddDynamicMap(type, pteKind, content)
            fastRequest<Boolean> {
                if (uri == null) {
                    SystemRepository.addDynamicRequest(map)
                } else {
                    val photo = withContext(Dispatchers.IO) {
                        SysNetConfig.buildPhotoPart(
                            this@AddDynamicActivity,
                            uri ?: Uri.EMPTY,
                            SysNetConfig.DynamicPicture
                        )
                    }
                    SystemRepository.addDynamicRequest(map, photo)
                }
            }?.let {
                ToastUtils.toastShort("发布成功")
                delay(1000)
                finish()
            }
        }

    }
}