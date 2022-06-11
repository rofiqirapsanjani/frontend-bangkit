package com.example.projectcapstone.ui.home

import android.os.Bundle
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcapstone.R
import com.example.projectcapstone.adapter.ProductsAdapter
import com.example.projectcapstone.authentication.register.RegisterActivity
import com.example.projectcapstone.databinding.FragmentHomeBinding
import com.example.projectcapstone.network.response.Product
import com.example.projectcapstone.network.response.ProductResponse
import com.example.projectcapstone.ui.productdetail.ProductDetail


class HomeFragment : Fragment() {
    private lateinit var rvproducts: RecyclerView
    private lateinit var products : List<Product>

    private lateinit var  _binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home ,container, false)
        rvproducts = view.findViewById(R.id.rv_products)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        getAllProducts()
        homeViewModel.products.observe(viewLifecycleOwner){
            products = it!!
            showProducts(products)
        }

    }

    fun getAllProducts() {
        homeViewModel.fetchProducts()
    }

    fun showProducts(products: List<Product>){
        rvproducts.layoutManager = LinearLayoutManager(activity)
        val adapter = ProductsAdapter(products)
        rvproducts.adapter = adapter
        adapter.setOnItemClickCallback(object : ProductsAdapter.OnItemClickCallback{
            override fun onItemClicked(product: Product) {
                val intent = Intent(activity, ProductDetail::class.java)
                intent.putExtra("product", product)
                System.out.println("getclicked")
                startActivity(intent)
            }
        })

    }
}
