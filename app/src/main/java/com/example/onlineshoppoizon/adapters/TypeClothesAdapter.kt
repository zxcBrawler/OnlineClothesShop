package com.example.onlineshoppoizon.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshoppoizon.databinding.CategoryCardBinding
import com.example.onlineshoppoizon.model.CategoryClothes
import com.example.onlineshoppoizon.model.TypeClothes

class TypeClothesAdapter (private val list: List<TypeClothes>): RecyclerView.Adapter<TypeClothesAdapter.TypeClothesViewHolder>() {
    private lateinit var mListener : OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(position: Long)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }
    class TypeClothesViewHolder(val binding: CategoryCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TypeClothesViewHolder {
        val itemView = CategoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TypeClothesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TypeClothesViewHolder, position: Int) {
        holder.binding.category.text = list[position].nameType
        holder.binding.next.setOnClickListener {
            mListener.onItemClick(list[position].idType)
        }
    }

    override fun getItemCount(): Int =
        list.size
}