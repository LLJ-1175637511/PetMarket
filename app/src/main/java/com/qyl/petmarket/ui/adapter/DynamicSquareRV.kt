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
import com.qyl.petmarket.data.vm.DynamicSquareVM
import com.qyl.petmarket.databinding.ItemDynamicSquareBinding
import com.qyl.petmarket.ui.activity.user.OtherUserActivity

class DynamicSquareRV(
    private val squareVm: DynamicSquareVM,
    val block: (bigPhoto: String) -> Unit
) :
    RecyclerView.Adapter<DynamicSquareRV.Holder>() {

    private val list = mutableListOf<DynamicBean>()

    inner class Holder(val binding: ItemDynamicSquareBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bindData(item: DynamicBean) {
            val photoHead = "http://47.110.231.180:8080"
            Glide.with(binding.root.context).load("${photoHead}${item.headPortrait}")
                .into(binding.ivHead)
            binding.tvName.text = item.author
            binding.tvTime.text = item.publishTime.replace('T', ' ')

            item.dynamicContent?.let {
                binding.tvContent.visibility = View.VISIBLE
                binding.tvContent.text = it
            }
            item.dynamicPicture?.let {
                val u = "${photoHead}${it}"
                binding.ivPhoto.visibility = View.VISIBLE
                Glide.with(binding.root.context).load(u).into(binding.ivPhoto)
                binding.ivPhoto.setOnClickListener {
                    block(u)
                }
            }
            binding.ivLike.init(item.isLiked, item.likedNums)

            binding.ivLike.setOnClickListener {
                squareVm.likeDynamic(item.id) {
                    binding.ivLike.clicked()
                }
            }

            binding.ivHead.setOnClickListener {
                binding.root.context.startActivity(
                    Intent(
                        binding.root.context,
                        OtherUserActivity::class.java
                    ).apply {
                        putExtra(OtherUserActivity.TAG_AUTHOR,item.author)
                    })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
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
    fun update(data: List<DynamicBean>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

}