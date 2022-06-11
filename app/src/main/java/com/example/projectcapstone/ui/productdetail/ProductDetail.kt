package com.example.projectcapstone.ui.productdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.projectcapstone.databinding.ActivityProductDetailBinding
import com.example.projectcapstone.network.response.Product
import com.example.projectcapstone.ui.order.OrderActivity

class ProductDetail : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product : Product? = intent.getParcelableExtra("product")
        val infoProduct = product

        binding.apply{
            idName.text = product!!.title
            price.text = product.price
            description.text = product.description
            Glide.with(this@ProductDetail)
                .load(product.img)
                .into(ivDetailProduct)

            cart.setOnClickListener{
                System.out.println("berhasil ditambahkan")
            }

            order.setOnClickListener{
                val intent = Intent(this@ProductDetail, OrderActivity::class.java)
                intent.putExtra("infoproduct", infoProduct)
                startActivity(intent)
            }
        }
    }

}
