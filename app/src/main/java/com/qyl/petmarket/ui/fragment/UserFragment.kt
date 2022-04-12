package com.qyl.petmarket.ui.fragment

import android.content.Intent
import androidx.fragment.app.activityViewModels
import com.qyl.petmarket.R
import com.qyl.petmarket.data.vm.MainVM
import com.qyl.petmarket.databinding.FragmentUserBinding
import com.qyl.petmarket.ui.activity.LoginActivity

class UserFragment : BaseFragment<FragmentUserBinding>() {

    override fun getLayoutId() = R.layout.fragment_user

    private val vm by activityViewModels<MainVM>()

    override fun initCreateView() {
        super.initCreateView()
        vm.userInfo.observe(this){
            mDataBinding.toolbar.toolbarBaseTitle.text = "个人资料"
            mDataBinding.tvEmail.text = "邮箱：${it.eamil}"
            mDataBinding.tvPhone.text = "电话：${it.telephone}"
            mDataBinding.tvInfo.text = "${it.gender}    ${(21..23).random()}   |   学生   |   ${it.address}"
            mDataBinding.tvNum.text = "现居住地：${it.address}"
            mDataBinding.tvUserName.text = it.userName
        }
        mDataBinding.tvQuit.setOnClickListener {
            requireActivity().startActivity(Intent(requireContext(),LoginActivity::class.java))
            requireActivity().finish()
        }
    }

}