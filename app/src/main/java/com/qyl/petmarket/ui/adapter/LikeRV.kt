package com.qyl.petmarket.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qyl.petmarket.R
import com.qyl.petmarket.data.bean.DynamicBean
import com.qyl.petmarket.data.bean.LikeRecordBean
import com.qyl.petmarket.databinding.ItemLikeRecordBinding
import com.qyl.petmarket.utils.CommonUtils
import com.qyl.petmarket.utils.convertGeLinTime

class LikeRV(private val list:List<LikeRecordBean>) : RecyclerView.Adapter<LikeRV.Holder>() {

    inner class Holder(val binding: ItemLikeRecordBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: LikeRecordBean){
            item.dynamicPicture?.let {
                binding.ivPhoto.visibility = View.VISIBLE
                val head = CommonUtils.convertUrl(it)
                Glide.with(binding.root.context).load(head).into(binding.ivPhoto)
            }
            val head = CommonUtils.convertUrl(item.likerPicture)
            Glide.with(binding.root.context).load(head).into(binding.ivHead)
            binding.tvName.text = item.liker
            binding.tvTime.text = item.time.convertGeLinTime()
            item.dynamicContent?.let {
                binding.tvContent.text = it
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = DataBindingUtil.inflate<ItemLikeRecordBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_like_record,
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount() = list.size

}