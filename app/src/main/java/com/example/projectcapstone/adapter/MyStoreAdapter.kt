package com.example.projectcapstone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectcapstone.R
import com.example.projectcapstone.network.response.Product

class MyStoreAdapter(val items: List<Product>) : RecyclerView.Adapter<MyStoreAdapter.MyStoreViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class MyStoreViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val tvbrand: TextView = view.findViewById(R.id.tv_brand_mystore)
        val  tvharga: TextView = view.findViewById(R.id.tv_harga_mystore)
        val  ivuser: ImageView = view.findViewById(R.id.iv_user)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyStoreViewHolder {
        return MyStoreViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_mystore, parent, false)
        )
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MyStoreViewHolder, position: Int) {
        val productStore = items[position]
        holder.tvbrand.text = productStore.title
        holder.tvharga.text =productStore.price
        Glide.with(holder.itemView.context).load(productStore.img).into(holder.ivuser)

        holder.itemView.setOnClickListener{
            System.out.println("click")
            onItemClickCallback.onItemClicked(items[holder.adapterPosition])
        }

        holder.itemView.setOnLongClickListener{
            System.out.println("Long click")
            onItemClickCallback.onLongItemClicked(items[holder.adapterPosition])
            return@setOnLongClickListener true
        }

    }

    interface OnItemClickCallback{
        fun onItemClicked(item : Product)
        fun onLongItemClicked(item : Product)
    }

}
