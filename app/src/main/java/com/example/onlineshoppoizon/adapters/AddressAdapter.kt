package com.example.onlineshoppoizon.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshoppoizon.databinding.AddressCardBinding
import com.example.onlineshoppoizon.databinding.CardItemLayoutBinding
import com.example.onlineshoppoizon.model.UserAddress
import com.example.onlineshoppoizon.model.UserCard

class AddressAdapter (private val addresses: List<UserAddress>): RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    private lateinit var mListener : OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(position: Long)
        fun onItemDelete(position: Long)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }
    class AddressViewHolder (listener: OnItemClickListener, val binding: AddressCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddressViewHolder {
        val itemView = AddressCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(mListener, itemView)
    }



    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.binding.nameAddress.text = addresses[position].address.nameAddress
        holder.binding.directionAddress.text = addresses[position].address.directionAddress
        holder.binding.city.text = addresses[position].address.city

        holder.binding.deleteAddress.setOnClickListener {
            mListener.onItemDelete(addresses[position].address.idAddress)
        }

    }

    override fun getItemCount(): Int  =
        addresses.size
}