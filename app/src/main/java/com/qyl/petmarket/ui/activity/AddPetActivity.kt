package com.qyl.petmarket.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import cn.leancloud.LCFile
import cn.leancloud.callback.ProgressCallback
import com.bumptech.glide.Glide
import com.qyl.petmarket.R
import com.qyl.petmarket.databinding.ActivityAddPetBinding
import com.qyl.petmarket.databinding.DialogChooseDateBinding
import com.qyl.petmarket.net.NetActivity
import com.qyl.petmarket.net.config.SysNetConfig
import com.qyl.petmarket.net.repository.SystemRepository
import com.qyl.petmarket.utils.PhotoUtils
import com.qyl.petmarket.utils.ToastUtils
import com.qyl.petmarket.utils.addZero
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class AddPetActivity : BaseAddPhotoActivity<ActivityAddPetBinding>() {

    override fun getLayoutId() = R.layout.activity_add_pet

    override fun init() {
        super.init()
        mDataBinding.toolbar.toolbarBaseTitle.text = "添加宠物"
        mDataBinding.birthday.text = INIT_BIRTHDAY
        mDataBinding.birthday.setOnClickListener {
            showDatePicker()
        }
        mDataBinding.ivPetIPhoto.setOnClickListener {
            choosePhoto()
        }
        mDataBinding.btAddPey.setOnClickListener {
            addPet()
        }
        buildLaunch {
            Glide.with(this).load(uri).into(mDataBinding.ivPetIPhoto)
        }
    }

    private fun addPet() {
        if (mDataBinding.birthday.text == INIT_BIRTHDAY) {
            ToastUtils.toastShort("请设置生日")
            return
        }
        if (mDataBinding.name.text.isEmpty()) {
            ToastUtils.toastShort("名称不能为空")
            return
        }
        if (uri == null) {
            ToastUtils.toastShort("未选择图片")
            return
        }
        reportPhoto()
    }

    private fun reportPhoto() {
        lifecycleScope.launch {
            val map = SysNetConfig.buildAddPetMap(
                mDataBinding.name.text.toString(),
                mDataBinding.birthday.text.toString(),
                mDataBinding.like.text.toString(),
                mDataBinding.disgusting.text.toString(),
            )
            val photo = withContext(Dispatchers.IO) {
                SysNetConfig.buildPhotoPart(
                    this@AddPetActivity,
                    uri ?: Uri.EMPTY,
                    SysNetConfig.PetPicture
                )
            }
            fastRequest<Boolean> {
                SystemRepository.addPetRequest(map,photo)
            }?.let {
                ToastUtils.toastShort("添加成功")
                setResult(RESULT_OK)
                delay(1000)
                finish()
            }
        }
    }

    private fun showDatePicker() {
        val binding = DialogChooseDateBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(binding.root)
            .create()
        dialog.setCancelable(false)
        binding.btSure.setOnClickListener {
            mDataBinding.birthday.text =
                "${binding.datePicker.year}-${(binding.datePicker.month).addZero()}-${(binding.datePicker.dayOfMonth).addZero()}"
            dialog.cancel()
        }
        dialog.show()
    }

    companion object {
        private const val INIT_BIRTHDAY = "设置生日"
    }
}