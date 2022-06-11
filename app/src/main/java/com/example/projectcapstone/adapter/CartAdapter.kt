package com.example.projectcapstone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcapstone.R
import com.example.projectcapstone.network.response.CartResponse


class CartAdapter(val carts: List<CartResponse>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvbrand: TextView = view.findViewById(R.id.tv_brand)
        val tvdeskripsi: TextView = view.findViewById(R.id.tv_deskripsi)
        val  tvharga: TextView = view.findViewById(R.id.tv_harga)
//        val  ivuser: ImageView = view.findViewById(R.id.iv_user)
//        Glide.with(itemView.context).load(stories.photoUrl).into(imgPhoto)
    }

    fun setOnItemClickCallback(onItemClickCallback: CartAdapter.OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_catalog, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = carts[position]
        holder.tvbrand.text =cart.cartId
//        holder.tvdeskripsi.text = cart.
//        holder.tvharga.text = cart.price.toString()

        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(carts[holder.adapterPosition])

        }
    }

    override fun getItemCount() = carts.size

    interface OnItemClickCallback{
        fun onItemClicked(cart : CartResponse)
    }
}
