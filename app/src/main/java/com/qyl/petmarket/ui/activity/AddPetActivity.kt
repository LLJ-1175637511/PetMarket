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
import com.qyl.petmarket.utils.PhotoUtils
import com.qyl.petmarket.utils.ToastUtils
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class AddPetActivity : BaseActivity<ActivityAddPetBinding>() {

    override fun getLayoutId() = R.layout.activity_add_pet

    var uri: Uri? = null

    @SuppressLint("CheckResult")
    private val launchPhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { ar ->
            if (ar.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            uri = ar.data?.data ?: return@registerForActivityResult
            Glide.with(this).load(uri).into(mDataBinding.ivPetIPhoto)
        }

    private val launchPhotoPermission =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { ar ->
            if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager())) {
                ToastUtils.toastShort("存储权限获取失败")
                finish()
                return@registerForActivityResult
            }
        }

    override fun init() {
        super.init()
        requestPermission()
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
        lifecycleScope.launch {

        }
    }

    private fun reportPhoto() {
        kotlin.runCatching {

        }.onFailure {
            ToastUtils.toastShort("文件上传失败：${it.message}")
        }
        lifecycleScope.launch {
            val file = withContext(Dispatchers.IO) {
                val path = PhotoUtils.getFileAbsolutePath(this@AddPetActivity, uri)
                val compressedImageFile = Compressor.compress(this@AddPetActivity, File(path)) {
                    quality(50)
                    format(Bitmap.CompressFormat.JPEG)
                    size(512_152) // 512kb
                }
                LCFile("${mDataBinding.name.text}.jpg", compressedImageFile.readBytes())
            }
            file.saveInBackground(object : ProgressCallback() {
                override fun done(percent: Int) {

                }
            })
            ToastUtils.toastShort("上传成功")
            delay(1000)
            finish()
        }
    }

    private fun choosePhoto() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE)
        galleryIntent.type = "image/*"
        this.launchPhoto.launch(galleryIntent)
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { //Android11（SDK版本30）
            // 先判断有没有权限
            if (!Environment.isExternalStorageManager()) { //判断是否获取到“允许管理所有文件”权限
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:" + getPackageName())
                launchPhotoPermission.launch(intent)
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
                "${binding.datePicker.year}年${binding.datePicker.month}月${binding.datePicker.dayOfMonth}日"
            dialog.cancel()
        }
        dialog.show()
    }

    companion object {
        private const val INIT_BIRTHDAY = "birthday"
    }
}