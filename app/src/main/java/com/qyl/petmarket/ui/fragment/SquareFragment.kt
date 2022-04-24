package com.qyl.petmarket.ui.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.qyl.petmarket.R
import com.qyl.petmarket.data.vm.DynamicVM
import com.qyl.petmarket.databinding.FragmentSquareBinding
import com.qyl.petmarket.ui.activity.AddDynamicActivity
import com.qyl.petmarket.ui.activity.SearchDynamicActivity

class SquareFragment : BaseFragment<FragmentSquareBinding>() {

    override fun getLayoutId() = R.layout.fragment_square

    private val tabList = arrayOf("日常", "科普", "好物")

    private val vm by activityViewModels<DynamicVM>()

    override fun initCreate() {
        super.initCreate()

    }

    override fun initCreateView() {
        super.initCreateView()
        initTab()
        mDataBinding.ivAdd.setOnClickListener {
            startCommonActivity<AddDynamicActivity>()
        }
        mDataBinding.ivSearch.setOnClickListener {
            startCommonActivity<SearchDynamicActivity>()
        }
    }

    inner class PageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount() = tabList.size

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> PetDynamicFragment(PetDynamicFragment.DynamicType.日常)
                1 -> PetDynamicFragment(PetDynamicFragment.DynamicType.科普)
                else -> PetDynamicFragment(PetDynamicFragment.DynamicType.好物)
            }
        }
    }

    @SuppressLint("NewApi")
    private fun initTab() {
        fun updateCustomView(tab: TabLayout.Tab, isSelect: Boolean, position: Int) {
            if (tab.customView == null) {
                tab.setCustomView(R.layout.tab_text)
            }
            tab.customView?.findViewById<TextView>(R.id.tab_text)?.apply {
                text = tabList[position]
                if (isSelect) {
                    setTextAppearance(R.style.TabLayoutTextSelected)
                } else {
                    setTextAppearance(R.style.TabLayoutTextUnSelected)
                }
            }
        }
        mDataBinding.viewPager.adapter = PageAdapter(requireActivity())
        TabLayoutMediator(mDataBinding.tabLayout, mDataBinding.viewPager) { tab, position ->
            if (position == 0) {
                updateCustomView(tab, true, position)
            }
            tab.text = tabList[position]
        }.attach()
        mDataBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTabSelected(tab: TabLayout.Tab) {
                updateCustomView(tab, true, tab.position)
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTabUnselected(tab: TabLayout.Tab) {
                updateCustomView(tab, false, tab.position)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }

}