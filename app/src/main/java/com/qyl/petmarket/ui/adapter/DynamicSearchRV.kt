package com.qyl.petmarket.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qyl.petmarket.R
import com.qyl.petmarket.data.bean.DynamicBean
import com.qyl.petmarket.data.vm.BigPhotoVm
import com.qyl.petmarket.data.vm.DynamicSquareVM
import com.qyl.petmarket.databinding.ItemDynamicSearchBinding


class DynamicSearchRV(private val vm: DynamicSquareVM, private val photoVm: BigPhotoVm) :
    RecyclerView.Adapter<DynamicSearchRV.Holder>() {

    private val list = mutableListOf<DynamicBean>()

    private var mSearchContent = ""

    inner class Holder(val binding: ItemDynamicSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: DynamicBean) {
            val photoHead = "http://47.110.231.180:8080"
            Glide.with(binding.root.context).load("${photoHead}${item.headPortrait}")
                .into(binding.ivHead)
            binding.tvName.text = item.author
            binding.tvType.text = item.dynamicKind
            binding.tvTime.text = item.publishTime.replace('T', ' ')
            item.dynamicContent?.let {
                if (mSearchContent.isEmpty()) return@let
                val style = SpannableStringBuilder(it)
                val s = it.indexOf(mSearchContent)
                style.setSpan(
                    ForegroundColorSpan(Color.RED),
                    if (s < 0) 0 else s,
                    s + mSearchContent.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding.tvContent.visibility = View.VISIBLE
                binding.tvContent.text = style
            }
            item.dynamicPicture?.let {
                val u = "${photoHead}${it}"
                binding.ivPhoto.visibility = View.VISIBLE
                Glide.with(binding.root.context).load(u).into(binding.ivPhoto)
                binding.ivPhoto.setOnClickListener {
                    photoVm.bigUrl.postValue(u)
                }
            }
            binding.ivLike.init(item.isLiked, item.likedNums)

            binding.ivLike.setOnClickListener {
                vm.likeDynamic(item.id) {
                    binding.ivLike.clicked()
                }
            }
        }

    }

    fun setContent(str: String) {
        mSearchContent = str
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = DataBindingUtil.inflate<ItemDynamicSearchBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_dynamic_search,
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindData(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(data: List<DynamicBean>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

}