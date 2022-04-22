package com.qyl.petmarket.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.qyl.petmarket.R
import com.qyl.petmarket.data.bean.PetBean
import com.qyl.petmarket.databinding.ItemPetBinding


class PetRV : RecyclerView.Adapter<PetRV.Holder>() {

    private val list = mutableListOf<PetBean>()

    class Holder(val binding: ItemPetBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(item:PetBean){

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = DataBindingUtil.inflate<ItemPetBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_pet,
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindData(list[position])
    }

    fun update(data:List<PetBean>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

}