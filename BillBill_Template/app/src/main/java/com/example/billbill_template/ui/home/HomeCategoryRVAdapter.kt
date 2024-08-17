package com.example.billbill_template.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.billbill_template.R
import com.example.billbill_template.databinding.ItemHomeCategoryBinding
import com.example.billbill_template.post.GetCategory
import com.example.billbill_template.post.GetCategoryManifestResponse

class HomeCategoryRVAdapter(private var result: GetCategoryManifestResponse) :
    RecyclerView.Adapter<HomeCategoryRVAdapter.ViewHolder>() {

    private var selectedPosition = 0

    companion object {
        var categoryList: MutableList<String> = mutableListOf()
    }

    interface HomeCategoryItemClickListener {
        fun onItemClick(name: String)
    }

    private lateinit var itemClickListener: HomeCategoryItemClickListener

    fun setHomeCategoryClickListener(itemClickListener: HomeCategoryItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun updateData(newResult: GetCategoryManifestResponse) {
        this.result = newResult
        notifyDataSetChanged()  // 데이터 변경을 어댑터에 알림
    }

    inner class ViewHolder(val binding: ItemHomeCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.homeCategoryItemTv

        fun bind(category: GetCategory, isSelected: Boolean) {
            if (isSelected) {
                binding.homeCategoryCardView.setCardBackgroundColor(
                    ContextCompat.getColor(binding.root.context, android.R.color.black)
                )
                binding.homeCategoryItemTv.setTextColor(
                    ContextCompat.getColor(binding.root.context, android.R.color.holo_orange_light)
                )
                binding.homeCategoryCardView.setBackgroundResource(R.drawable.selected_background) // 선택된 배경
            } else {
                binding.homeCategoryCardView.setCardBackgroundColor(
                    ContextCompat.getColor(binding.root.context, android.R.color.transparent)
                )
                binding.homeCategoryItemTv.setTextColor(
                    ContextCompat.getColor(binding.root.context, android.R.color.black)
                )
                binding.homeCategoryCardView.setBackgroundResource(R.drawable.default_background) // 기본 배경
            }

            binding.root.setOnClickListener {
                if (selectedPosition != adapterPosition) {
                    val previousPosition = selectedPosition
                    selectedPosition = adapterPosition
                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)
                }
                itemClickListener.onItemClick(category.name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return result.categories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < result.categories.size) {
            val category = result.categories[position]
            holder.bind(category, position == selectedPosition)
            if (category.id < 1000) {
                if (!categoryList.contains(category.name)) {
                    categoryList.add(category.name)
                }
                holder.name.text = category.name
            }
        }
    }
}
