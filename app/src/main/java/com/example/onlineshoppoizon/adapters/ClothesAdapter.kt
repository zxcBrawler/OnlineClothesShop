package com.example.onlineshoppoizon.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.databinding.ItemCardBinding
import com.example.onlineshoppoizon.model.Clothes
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ClothesAdapter(private val clothesList: List<Clothes>): RecyclerView.Adapter<ClothesAdapter.ClothesViewHolder>() {

    private lateinit var mListener : OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }
    class ClothesViewHolder(listener: OnItemClickListener, val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothesViewHolder {
        val itemView = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClothesViewHolder(mListener, itemView)
    }

    override fun getItemCount(): Int {
        return clothesList.size
    }

    override fun onBindViewHolder(holder: ClothesViewHolder, position: Int) {
        holder.binding.itemName.text = clothesList[position].nameClothesEn
        holder.binding.itemPrice.text = clothesList[position].priceClothes
        Picasso.get().load(clothesList[position].clothesPhoto).fit()
            .error(R.drawable.close).into(holder.binding.itemPhoto, object : Callback {
                override fun onSuccess() {
                    // Image loaded successfully
                }

                override fun onError(e: Exception?) {
                    // Handle the error
                    e?.printStackTrace()
                }
            })// Error image.into(holder.binding.itemPhoto)
    }
}