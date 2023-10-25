package com.example.onlineshoppoizon.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshoppoizon.databinding.AvailabilityCardBinding
import com.example.onlineshoppoizon.model.ShopGarnish

class ItemAvailabilityAdapter(private val shopGarnish: List<ShopGarnish>): RecyclerView.Adapter<ItemAvailabilityAdapter.AvailabilityViewHolder>() {
    private lateinit var mListener : OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: ItemAvailabilityAdapter.OnItemClickListener){
        mListener = listener
    }
    class AvailabilityViewHolder(listener: OnItemClickListener, val binding: AvailabilityCardBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemAvailabilityAdapter.AvailabilityViewHolder {
        val itemView = AvailabilityCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemAvailabilityAdapter.AvailabilityViewHolder(mListener, itemView)
    }

    override fun onBindViewHolder(
        holder: ItemAvailabilityAdapter.AvailabilityViewHolder,
        position: Int
    ) {
        holder.binding.count.text = shopGarnish[position].quantity.toString()
        holder.binding.shopName.text = shopGarnish[position].shopAddresses.shopAddressDirection
        holder.binding.contactInfo.text = shopGarnish[position].shopAddresses.shopMetro
    }

    override fun getItemCount(): Int
    = shopGarnish.size

}