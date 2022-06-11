package com.example.projectcapstone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcapstone.R
import com.example.projectcapstone.network.response.WishListResponse

class WishListAdapter(val wishlist: List<WishListResponse>) : RecyclerView.Adapter<WishListAdapter.ListViewHolder>(){

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvbrand: TextView = view.findViewById(R.id.tv_brand)
        val tvdeskripsi: TextView = view.findViewById(R.id.tv_deskripsi)
        val  tvharga: TextView = view.findViewById(R.id.tv_harga)
//        val  ivuser: ImageView = holder.view.findViewById(R.id.iv_user)
//        Glide.with(itemView.context).load(stories.photoUrl).into(imgPhoto)

        fun bind(wish: WishListResponse) {
//            tvUsername.text = products.name
//            tvDescription.text = products.description
            itemView.setOnClickListener {
//                val intent = Intent(itemView.context, StoryDetailActivity::class.java)
//                intent.putExtra("story", stories)
                System.out.println("getclicked")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_catalog, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val wish = wishlist[position]
        holder.tvbrand.text =wish.title
        holder.tvdeskripsi.text = wish.desc
        holder.tvharga.text = wish.price.toString()
        holder.bind(wish)
    }

    override fun getItemCount() = wishlist.size
}
