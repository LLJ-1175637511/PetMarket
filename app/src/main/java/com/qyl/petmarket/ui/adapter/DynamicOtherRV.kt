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
import com.qyl.petmarket.data.vm.DynamicUserVM
import com.qyl.petmarket.databinding.ItemDynamicUserBinding
import com.qyl.petmarket.net.config.SysNetConfig
import com.qyl.petmarket.utils.CommonUtils


class DynamicOtherRV(private val dynamicUserVM: DynamicUserVM, private val photoVm: BigPhotoVm) :
    RecyclerView.Adapter<DynamicOtherRV.Holder>() {

    private val list = mutableListOf<DynamicBean>()

    private var mSearchContent = ""

    inner class Holder(val binding: ItemDynamicUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: DynamicBean) {
            val photoHead = CommonUtils.convertUrl(item.headPortrait)
            Glide.with(binding.root.context).load(photoHead).into(binding.ivHead)
            binding.tvName.text = item.author
            binding.tvType.text = item.dynamicKind
            binding.tvTime.text = item.publishTime.replace('T', ' ')
            item.dynamicContent?.let {
                if (mSearchContent.isNotEmpty()) {
                    val style = SpannableStringBuilder(it)
                    val s = it.indexOf(mSearchContent)
                    style.setSpan(
                        ForegroundColorSpan(Color.RED),
                        if (s < 0) 0 else s,
                        s + mSearchContent.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    binding.tvContent.text = style
                }else{
                    binding.tvContent.text = it
                }
                binding.tvContent.visibility = View.VISIBLE
            }
            item.dynamicPicture?.let {
                val u = "${photoHead}${it}"
                binding.ivPhoto.visibility = View.VISIBLE
                Glide.with(binding.root.context).load(u).into(binding.ivPhoto)
                binding.ivPhoto.setOnClickListener {
                    photoVm.bigUrl.postValue(u)
                }
            }

            if (item.author == SysNetConfig.getUserName() && mSearchContent.isEmpty()){
                binding.tvDelete.visibility = View.VISIBLE
                binding.tvDelete.setOnClickListener {
                    dynamicUserVM.deleteDynamic(item.id){
                        removeItem(adapterPosition)
                    }
                }
            }

            binding.ivLike.init(item.isLiked, item.likedNums)

            binding.ivLike.setOnClickListener {
                dynamicUserVM.likeDynamic(item.id) {
                    binding.ivLike.clicked()
                }
            }
        }
    }

    fun removeItem(p:Int){
        list.removeAt(p)
        notifyItemRemoved(p)
    }

    fun setContent(str: String) {
        mSearchContent = str
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = DataBindingUtil.inflate<ItemDynamicUserBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_dynamic_user,
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