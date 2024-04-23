package com.example.onlineshoppoizon.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshoppoizon.databinding.ColorsCardBinding
import com.example.onlineshoppoizon.databinding.SizeCardBinding
import com.example.onlineshoppoizon.model.ClothesSizeClothes
import com.example.onlineshoppoizon.model.Colors
import com.example.onlineshoppoizon.model.SizeClothes

class SizesAdapter(private val sizesList: List<ClothesSizeClothes>): RecyclerView.Adapter<SizesAdapter.SizesViewHolder>()  {
    private lateinit var mListener : OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: SizesAdapter.OnItemClickListener){
        mListener = listener
    }
    class SizesViewHolder(listener: OnItemClickListener, val binding: SizeCardBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.sizeButton.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SizesViewHolder {
        val itemView = SizeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SizesViewHolder(mListener, itemView)
    }

    override fun onBindViewHolder(holder: SizesViewHolder, position: Int) {
        holder.binding.sizeButton.text = sizesList[position].sizeClothes.nameSize
    }

    override fun getItemCount(): Int
    = sizesList.size
}