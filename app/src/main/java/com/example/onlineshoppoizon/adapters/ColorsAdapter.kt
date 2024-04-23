package com.example.onlineshoppoizon.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshoppoizon.databinding.ColorsCardBinding
import com.example.onlineshoppoizon.model.Clothes
import com.example.onlineshoppoizon.model.ClothesColors
import com.example.onlineshoppoizon.model.Colors


class ColorsAdapter(private val colorsList: List<ClothesColors>): RecyclerView.Adapter<ColorsAdapter.ColorsViewHolder>() {
    private lateinit var mListener : OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: ColorsAdapter.OnItemClickListener){
        mListener = listener
    }
    class ColorsViewHolder(listener: OnItemClickListener, val binding: ColorsCardBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.colorButton.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ColorsViewHolder {
        val itemView = ColorsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorsViewHolder(mListener, itemView)
    }

    override fun onBindViewHolder(holder: ColorsViewHolder, position: Int) {
        holder.binding.colorButton.setBackgroundColor(Color.parseColor(colorsList[position].colors.hex))
    }

    override fun getItemCount(): Int =
        colorsList.size
}