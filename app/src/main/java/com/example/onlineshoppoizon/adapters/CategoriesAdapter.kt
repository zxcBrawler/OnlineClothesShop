package com.example.onlineshoppoizon.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshoppoizon.databinding.CategoryCardBinding
import com.example.onlineshoppoizon.model.CategoryClothes

class CategoriesAdapter (private val list: List<CategoryClothes>): RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    private lateinit var mListener : OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(position: Long)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }
    class CategoriesViewHolder(val binding: CategoryCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesViewHolder {
        val itemView = CategoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
       holder.binding.category.text = list[position].nameCategory
        holder.binding.next.setOnClickListener {
            mListener.onItemClick(list[position].id)
        }
    }

    override fun getItemCount(): Int =
        list.size
}