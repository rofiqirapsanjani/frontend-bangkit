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

class ProductsAdapter(val products: List<Product>) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvbrand: TextView = view.findViewById(R.id.tv_brand)
        val tvdeskripsi: TextView = view.findViewById(R.id.tv_deskripsi)
        val  tvharga: TextView = view.findViewById(R.id.tv_harga)
        val  ivuser: ImageView = view.findViewById(R.id.iv_user)

    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_catalog, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.tvbrand.text =product.title
        holder.tvdeskripsi.text = product.description
        holder.tvharga.text = product.price
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(products[holder.adapterPosition])
        }
        Glide.with(holder.itemView.context).load(product.img).into(holder.ivuser)
    }
    override fun getItemCount() = products.size

    interface OnItemClickCallback{
        fun onItemClicked(product : Product)
    }
}