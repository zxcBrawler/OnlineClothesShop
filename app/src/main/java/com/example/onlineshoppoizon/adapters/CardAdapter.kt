package com.example.onlineshoppoizon.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshoppoizon.databinding.CardItemLayoutBinding
import com.example.onlineshoppoizon.databinding.ItemInCartCardBinding
import com.example.onlineshoppoizon.model.UserCard

class CardAdapter (private val cards: List<UserCard>): RecyclerView.Adapter<CardAdapter.CardViewHolder>()  {
    private lateinit var mListener : OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(position: Long)
        fun onItemDelete(position: Long)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }
    class CardViewHolder (listener: OnItemClickListener, val binding: CardItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = CardItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(mListener, itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.binding.orderNum.text = cards[position].card.cardNum
        holder.binding.deleteCard.setOnClickListener {
            mListener.onItemDelete(cards[position].card.id)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int =
        cards.size
}