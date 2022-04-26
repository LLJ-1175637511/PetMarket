package com.qyl.petmarket.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qyl.petmarket.R
import com.qyl.petmarket.data.bean.PetBean
import com.qyl.petmarket.databinding.ItemPetOtherBinding
import com.qyl.petmarket.utils.convertGeLinTime

class PetOtherUserRV(
    private val list: List<PetBean>,
    val block: (bigPhoto: String) -> Unit
) : RecyclerView.Adapter<PetOtherUserRV.Holder>() {

    inner class Holder(val binding: ItemPetOtherBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: PetBean) {
            val url = "http://47.110.231.180:8080${item.petPicture}"
            Glide.with(binding.root.context).load(url).into(binding.ivPhoto)
            binding.tvLike.text = "喜好：${item.like}"
            binding.tvTaboo.text = "禁忌：${item.taboo}"
            binding.tvBirthday.text = "生日：${item.birthday.convertGeLinTime()}"
            binding.tvName.text = item.petName
            binding.ivPhoto.setOnClickListener {
                block(url)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = DataBindingUtil.inflate<ItemPetOtherBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_pet_other,
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