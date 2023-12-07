package com.example.onlineshoppoizon.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshoppoizon.databinding.CardPaymentBinding
import com.example.onlineshoppoizon.model.UserCard


class CardPaymentAdapter (private val cards: List<UserCard>): RecyclerView.Adapter<CardPaymentAdapter.CardViewHolder>()  {
    private var lastCheckedRB: CompoundButton? = null
    private lateinit var mListener : OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(position: Long)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }
    class CardViewHolder (val binding: CardPaymentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = CardPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.binding.orderNum.text = cards[position].card.cardNum
        holder.binding.selectCard.setOnCheckedChangeListener {
                buttonView, isChecked ->
            lastCheckedRB?.apply { setChecked(!isChecked) }
            lastCheckedRB = buttonView.apply { setChecked(isChecked) }
            mListener.onItemClick(cards[holder.bindingAdapterPosition].id)
        }
        if (holder.binding.selectCard.isChecked) lastCheckedRB = holder.binding.selectCard
    }


    override fun getItemCount(): Int =
        cards.size
}