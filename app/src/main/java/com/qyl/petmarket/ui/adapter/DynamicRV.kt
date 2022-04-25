package com.qyl.petmarket.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qyl.petmarket.R
import com.qyl.petmarket.data.bean.DynamicBean
import com.qyl.petmarket.data.bean.PetBean
import com.qyl.petmarket.data.vm.DynamicSquareVM
import com.qyl.petmarket.data.vm.PetVM
import com.qyl.petmarket.databinding.ItemDynamicSquareBinding
import com.qyl.petmarket.databinding.ItemPetBinding
import com.qyl.petmarket.ui.activity.UpdatePetActivity
import com.qyl.petmarket.utils.convertGeLinAllTime
import com.qyl.petmarket.utils.convertGeLinTime

class DynamicRV(private val vm: DynamicSquareVM) : RecyclerView.Adapter<DynamicRV.Holder>() {

    private val list = mutableListOf<DynamicBean>()

    inner class Holder(val binding: ItemDynamicSquareBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: DynamicBean){
            val photoHead = "http://47.110.231.180:8080"
            Glide.with(binding.root.context).load("${photoHead}${item.headPortrait}").into(binding.ivHead)
            binding.tvName.text = item.author
            binding.tvTime.text = item.publishTime.replace('T',' ')
            binding.tvLikeCount.text = item.likedNums.toString()
            item.dynamicContent?.let {
                binding.tvContent.visibility = View.VISIBLE
                binding.tvContent.text = it
            }
            item.dynamicPicture?.let {
                val u = "${photoHead}${it}"
                binding.ivPhoto.visibility = View.VISIBLE
                Glide.with(binding.root.context).load(u).into(binding.ivPhoto)
                binding.ivPhoto.setOnClickListener {
                    vm.bigUrl.postValue(u)
                }
            }
            binding.ivLike.setOnClickListener {

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):Holder {
        val binding = DataBindingUtil.inflate<ItemDynamicSquareBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_dynamic_square,
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindData(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(data:List<DynamicBean>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

}