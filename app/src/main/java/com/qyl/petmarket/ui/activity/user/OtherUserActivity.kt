package com.qyl.petmarket.ui.activity.user

import android.view.View
import androidx.activity.viewModels
import com.qyl.petmarket.R
import com.qyl.petmarket.data.vm.BigPhotoVm
import com.qyl.petmarket.data.vm.DynamicUserVM
import com.qyl.petmarket.data.vm.PetVM
import com.qyl.petmarket.databinding.ActivityUserOtherBinding
import com.qyl.petmarket.ui.activity.BaseActivity
import com.qyl.petmarket.ui.adapter.DynamicOtherRV
import com.qyl.petmarket.ui.adapter.PetOtherUserRV
import com.qyl.petmarket.utils.ToastUtils

class OtherUserActivity : BaseActivity<ActivityUserOtherBinding>() {

    override fun getLayoutId() = R.layout.activity_user_other

    private lateinit var mDynamicAdapter: DynamicOtherRV
    private lateinit var mPetAdapter: PetOtherUserRV

    private val dynamicVm by viewModels<DynamicUserVM>()
    private val petVm by viewModels<PetVM>()
    private val photoVm by viewModels<BigPhotoVm>()

    private var authorName: String? = null

    override fun init() {
        super.init()
        initMainView()
        dynamicVm.queryUserDynamic(authorName)
        petVm.getPetInfo(authorName)
    }

    private fun initMainView() {
        authorName = intent.getStringExtra(TAG_AUTHOR)
        if (authorName == null) {
            ToastUtils.toastShort("数据错误")
            finish()
            return
        }
        mDataBinding.toolbar.toolbarBaseTitle.text = "${authorName} 的主页"
        mDynamicAdapter = DynamicOtherRV(dynamicVm, photoVm)

        mDataBinding.rvDynamic.adapter = mDynamicAdapter
        photoVm.bigUrl.observe(this) {
            if (it == null) {
                mDataBinding.flBG.hide()
                return@observe
            }
            mDataBinding.flBG.loadBigPhoto(it)
        }
        petVm.petList.observe(this) { pList ->
            if (pList != null && pList.isNotEmpty()) {
                mDataBinding.rvPet.visibility = View.VISIBLE
                mPetAdapter = PetOtherUserRV(pList) {
                    photoVm.bigUrl.postValue(it)
                }
                mDataBinding.rvPet.adapter = mPetAdapter
            }
        }
        dynamicVm.searchList.observe(this) {
            mDynamicAdapter.update(it ?: emptyList())
        }
    }

    companion object {
        const val TAG_AUTHOR = "tag_author"
    }
}