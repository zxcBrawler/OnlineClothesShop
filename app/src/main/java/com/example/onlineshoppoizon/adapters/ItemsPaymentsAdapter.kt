package com.example.onlineshoppoizon.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshoppoizon.databinding.ItemCardBinding
import com.example.onlineshoppoizon.databinding.PaymentItemCardBinding
import com.example.onlineshoppoizon.model.Cart
import com.example.onlineshoppoizon.model.Clothes
import com.squareup.picasso.Picasso

class ItemsPaymentsAdapter (private val clothesList: List<Cart>): RecyclerView.Adapter<ItemsPaymentsAdapter.ItemsPaymentViewHolder>() {
    class ItemsPaymentViewHolder (val binding: PaymentItemCardBinding)  : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemsPaymentViewHolder {
        val itemView = PaymentItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemsPaymentViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ItemsPaymentViewHolder,
        position: Int
    ) {
        holder.binding.itemName.text = clothesList[position].colorClothesCart.clothes.nameClothes
        holder.binding.itemPrice.text =
            "${clothesList[position].quantity} X ${clothesList[position].colorClothesCart.clothes.priceClothes}"
        holder.binding.itemSize.text = clothesList[position].sizeClothes.sizeClothes.nameSize
        Picasso.get().load(clothesList[position].colorClothesCart.clothes.clothesPhoto).fit().into(holder.binding.itemPhoto)
    }

    override fun getItemCount(): Int =
        clothesList.size
}