package com.example.onlineshoppoizon.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshoppoizon.databinding.PaymentItemCardBinding
import com.example.onlineshoppoizon.model.Cart
import com.example.onlineshoppoizon.model.OrderComposition
import com.squareup.picasso.Picasso

class OrderCompositionAdapter (private val clothesList: List<OrderComposition>): RecyclerView.Adapter<OrderCompositionAdapter.OrderCompViewHolder>() {
    class OrderCompViewHolder (val binding: PaymentItemCardBinding)  : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderCompViewHolder {
        val itemView = PaymentItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderCompViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: OrderCompViewHolder,
        position: Int
    ) {
        holder.binding.itemName.text = clothesList[position].clothesComp.nameClothesEn
        holder.binding.itemPrice.text =
            "${clothesList[position].quantity} X ${clothesList[position].clothesComp.priceClothes}"
        holder.binding.itemSize.text = clothesList[position].sizeClothes.nameSize
        Picasso.get().load(clothesList[position].clothesComp.clothesPhoto).fit().into(holder.binding.itemPhoto)
    }

    override fun getItemCount(): Int =
        clothesList.size
}