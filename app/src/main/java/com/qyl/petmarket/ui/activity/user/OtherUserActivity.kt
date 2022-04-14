package com.qyl.petmarket.ui.activity.user

import androidx.lifecycle.lifecycleScope
import cn.leancloud.LCQuery
import cn.leancloud.LCUser
import com.qyl.petmarket.R
import com.qyl.petmarket.databinding.ActivityOtherUserBinding
import com.qyl.petmarket.ui.activity.BaseActivity
import com.qyl.petmarket.utils.LCUtils
import com.qyl.petmarket.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_other_user.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OtherUserActivity : BaseActivity<ActivityOtherUserBinding>() {

    override fun getLayoutId() = R.layout.activity_other_user

    private var objId = ""

    override fun init() {
        super.init()

        objId = intent.getStringExtra(OTHER_USER_ID).toString()
        if (objId.isEmpty()) {
            ToastUtils.toastShort("用户ID不能为空")
            finish()
            return
        }
        mDataBinding.toolbar.toolbarBaseTitle.text = "ta的资料"
        queryOtherUserInfo()
        tvLike.setOnClickListener {

        }
        tvDynamic.setOnClickListener {

        }

    }

    private fun queryOtherUserInfo() {
        val userObj = LCQuery<LCUser>("_User")
        lifecycleScope.launch {
            val user = withContext(Dispatchers.IO) {

                userObj.get(objId)
            }
            val text =
                "${user.getString(LCUtils.LCUserSex)}    ${user.get(LCUtils.LCUserAge)}   |   ${
                    user.getString(
                        LCUtils.LCUserAddress
                    )
                }"
            mDataBinding.tvUserInfo.text = text
            mDataBinding.tvLikeCount.text = user.getString(LCUtils.LCUserLikeCount)
            mDataBinding.tvUserName.text = user.getString(LCUtils.LCUserAlias)
        }
    }

    companion object {
        const val OTHER_USER_ID = "other_user_id"
    }
}