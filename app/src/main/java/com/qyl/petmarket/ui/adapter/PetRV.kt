package com.qyl.petmarket.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qyl.petmarket.R
import com.qyl.petmarket.data.bean.PetBean
import com.qyl.petmarket.data.vm.PetVM
import com.qyl.petmarket.databinding.ItemPetMineBinding
import com.qyl.petmarket.ui.activity.UpdatePetActivity
import com.qyl.petmarket.utils.convertGeLinTime

class PetRV(private val vm: PetVM,val block: (bigPhoto: String) -> Unit) : RecyclerView.Adapter<PetRV.Holder>() {

    private val list = mutableListOf<PetBean>()

    inner class Holder(val binding: ItemPetMineBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: PetBean){
            binding.ivPhoto
            val url = "http://47.110.231.180:8080${item.petPicture}"
            Glide.with(binding.root.context).load(url).into(binding.ivPhoto)
            binding.tvLike.text = "喜好：${item.like}"
            binding.tvTaboo.text = "禁忌：${item.taboo}"
            binding.tvBirthday.text = "生日：${item.birthday.convertGeLinTime()}"
            binding.tvName.text = item.petName
            binding.tvDelete.setOnClickListener {
                vm.deletePet(item.id){
                    removeItem(adapterPosition)
                }
            }
            binding.ivPhoto.setOnClickListener {
                block(url)
            }
            binding.tvRevise.setOnClickListener {
                it.context.startActivity(Intent(it.context,UpdatePetActivity::class.java).apply {
                    putExtra(UpdatePetActivity.TAG_UPDATE,item)
                })
            }
        }
    }

    fun removeItem(p:Int){
        list.removeAt(p)
        notifyItemRemoved(p)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = DataBindingUtil.inflate<ItemPetMineBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_pet_mine,
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindData(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(data:List<PetBean>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

}