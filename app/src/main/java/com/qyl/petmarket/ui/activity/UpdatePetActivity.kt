package com.qyl.petmarket.ui.activity

import android.net.Uri
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.qyl.petmarket.R
import com.qyl.petmarket.data.bean.PetBean
import com.qyl.petmarket.databinding.ActivityUpdatePetBinding
import com.qyl.petmarket.databinding.DialogChooseDateBinding
import com.qyl.petmarket.net.config.SysNetConfig
import com.qyl.petmarket.net.repository.SystemRepository
import com.qyl.petmarket.utils.CommonUtils
import com.qyl.petmarket.utils.ToastUtils
import com.qyl.petmarket.utils.addZero
import com.qyl.petmarket.utils.convertGeLinTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UpdatePetActivity : BaseAddPhotoActivity<ActivityUpdatePetBinding>() {

    override fun getLayoutId() = R.layout.activity_update_pet

    private lateinit var petBean: PetBean

    override fun init() {
        super.init()
        val itemBean = intent.getParcelableExtra<PetBean>(TAG_UPDATE)
        if (itemBean == null){
            ToastUtils.toastShort("数据错误")
            finish()
            return
        }
        petBean = itemBean
        mDataBinding.toolbar.toolbarBaseTitle.text = "修改宠物"
        mDataBinding.birthday.text = petBean.birthday.convertGeLinTime()
        mDataBinding.name.setText(petBean.petName)
        mDataBinding.like.setText(petBean.like)
        mDataBinding.disgusting.setText(petBean.taboo)
        mDataBinding.birthday.setOnClickListener {
            showDatePicker()
        }
        mDataBinding.ivPetIPhoto.setOnClickListener {
            choosePhoto()
        }
        mDataBinding.btAddPey.setOnClickListener {
            updatePet()
        }
        val url = CommonUtils.convertUrl(petBean.petPicture)
        Glide.with(this).load(url).into(mDataBinding.ivPetIPhoto)

        buildLaunch {
            Glide.with(this).load(uri).into(mDataBinding.ivPetIPhoto)
        }
    }

    private fun updatePet() {
        if (mDataBinding.name.text.isEmpty()) {
            ToastUtils.toastShort("名称不能为空")
            return
        }
        lifecycleScope.launch {
            val map = SysNetConfig.buildUpdatePetMap(
                petBean.id,
                mDataBinding.name.text.toString(),
                mDataBinding.birthday.text.toString(),
                mDataBinding.like.text.toString(),
                mDataBinding.disgusting.text.toString(),
            )

            fastRequest<Boolean> {
                if (uri == null){
                    SystemRepository.updatePetRequest(map)
                }else{
                    val photo = withContext(Dispatchers.IO) {
                        SysNetConfig.buildPhotoPart(
                            this@UpdatePetActivity,
                            uri ?: Uri.EMPTY,
                            SysNetConfig.PetPicture
                        )
                    }
                    SystemRepository.updatePetRequest(map,photo)
                }
            }?.let {
                ToastUtils.toastShort("修改成功")
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
        const val TAG_UPDATE = "tag_update"
    }
}