package com.example.onlineshoppoizon.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshoppoizon.databinding.OrderCardBinding
import com.example.onlineshoppoizon.model.UserOrder

class OrderAdapter (private val list : List<UserOrder>) : RecyclerView.Adapter<OrderAdapter.UserOrderViewHolder>()  {
    private lateinit var mListener : OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    class UserOrderViewHolder (listener: OnItemClickListener, val binding: OrderCardBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserOrderViewHolder {
        val itemView = OrderCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserOrderViewHolder(mListener, itemView)
    }

    override fun onBindViewHolder(holder: UserOrderViewHolder, position: Int) {
        holder.binding.orderNum.text = list[position].orders.numberOrder
        holder.binding.orderDate.text = list[position].orders.dateOrder
        holder.binding.orderSum.text = list[position].orders.sumOrder
    }

    override fun getItemCount(): Int  =
        list.size
}