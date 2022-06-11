package com.example.projectcapstone.ui.mystore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcapstone.R
import com.example.projectcapstone.adapter.ProductsAdapter
import com.example.projectcapstone.databinding.ActivityMyStoreBinding
import com.example.projectcapstone.network.response.Product


class MyStoreActivity : AppCompatActivity() {

    private lateinit var rvproducts: RecyclerView
    private lateinit var products : List<Product>

    private lateinit var binding: ActivityMyStoreBinding
    private lateinit var myStoreViewModel: MyStoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_store)

    }


    fun getAllProducts() {
        myStoreViewModel.fetchProducts()
    }

    fun showProducts(products: List<Product>){


    }
}