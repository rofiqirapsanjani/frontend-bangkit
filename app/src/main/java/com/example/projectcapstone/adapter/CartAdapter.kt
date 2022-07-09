package com.example.projectcapstone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectcapstone.R
import com.example.projectcapstone.network.response.CartResponse
import com.example.projectcapstone.ui.cart.CartFragment
import com.example.projectcapstone.ui.cart.CartViewModel

class CartAdapter(val carts: List<CartResponse>, val access_token : String) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_catalog_cart, parent, false)
        )
    }

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvbrand: TextView = view.findViewById(R.id.tv_brand)
        val  tvharga: TextView = view.findViewById(R.id.tv_harga)
        val  ivuser: ImageView = view.findViewById(R.id.iv_user)

    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = carts[position]
        holder.tvbrand.text =cart.title
        holder.tvharga.text = cart.price
        holder.itemView.setOnClickListener{
            System.out.println("click")
            onItemClickCallback.onItemClicked(carts[holder.adapterPosition])
        }
        Glide.with(holder.itemView.context).load(cart.img).into(holder.ivuser)

        holder.itemView.setOnLongClickListener{
            System.out.println("Long click")
            onItemClickCallback.onLongItemClicked(carts[holder.adapterPosition])
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount() = carts.size

    interface OnItemClickCallback{
        fun onItemClicked(cart : CartResponse)
        fun onLongItemClicked(cart : CartResponse)
    }
}
