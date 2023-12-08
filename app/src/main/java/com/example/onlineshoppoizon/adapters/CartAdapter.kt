package com.example.onlineshoppoizon.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshoppoizon.databinding.ItemCardBinding
import com.example.onlineshoppoizon.databinding.ItemInCartCardBinding
import com.example.onlineshoppoizon.model.Cart
import com.example.onlineshoppoizon.model.Clothes
import com.example.onlineshoppoizon.repository.CartRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.viewmodel.CartViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class CartAdapter(private val cart: List<Cart>): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private lateinit var mListener : OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Long)
        fun onDeleteItem(position: Long)
        fun onAddItem(position: Long)
        fun onDecreaseItem(position: Long)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }
    class CartViewHolder(listener: OnItemClickListener, val binding: ItemInCartCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = ItemInCartCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(mListener, itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.binding.itemName.text = cart[position].sizeClothes.clothes.nameClothes
        holder.binding.itemPrice.text = cart[position].sizeClothes.clothes.priceClothes
        holder.binding.itemSize.text = cart[position].sizeClothes.sizeClothes.nameSize
        holder.binding.itemQuantity.text = cart[position].quantity
        Picasso.get().load(cart[position].sizeClothes.clothes.clothesPhoto).fit().into(holder.binding.itemPhoto)

        holder.binding.addItem.setOnClickListener {
           mListener.onAddItem(cart[position].id)
        }
        holder.binding.decreaseItem.setOnClickListener {
            mListener.onDecreaseItem(cart[position].id)
        }
        holder.binding.deleteItem.setOnClickListener{
            mListener.onDeleteItem(cart[position].id)
        }
        holder.binding.itemPhoto.setOnClickListener {
            mListener.onItemClick(cart[position].sizeClothes.clothes.idClothes.toLong())
        }

    }

    override fun getItemCount(): Int  =
        cart.size

}

