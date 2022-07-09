package com.example.projectcapstone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectcapstone.R
import com.example.projectcapstone.network.response.OrderResponse

class OrderHistoryAdapter(val histories: List<OrderResponse>) : RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvbrand: TextView = view.findViewById(R.id.tv_brand_history)
        val tvharga: TextView = view.findViewById(R.id.tv_harga_history)
        val ivuser: ImageView = view.findViewById(R.id.iv_user)
    }

    fun setOnItemClickCallback(onItemClickCallback: OrderHistoryAdapter.OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_catalog_orderhist, parent, false)
        )
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val history = histories[position]
        holder.tvbrand.text = history.title
        holder.tvharga.text = history.amount
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(histories[holder.adapterPosition])
        }
        holder.itemView.setOnLongClickListener{
            onItemClickCallback.onLongItemClicked(histories[holder.adapterPosition])
            return@setOnLongClickListener true
        }
        Glide.with(holder.itemView.context).load(history.img).into(holder.ivuser)

    }

    override fun getItemCount() = histories.size

    interface OnItemClickCallback{
        fun onItemClicked( history: OrderResponse)
        fun onLongItemClicked(history: OrderResponse)
    }
}
