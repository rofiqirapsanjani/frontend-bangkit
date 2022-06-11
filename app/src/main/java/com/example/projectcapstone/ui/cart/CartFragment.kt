package com.example.projectcapstone.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcapstone.R
import com.example.projectcapstone.adapter.CartAdapter
import com.example.projectcapstone.databinding.FragmentCartBinding
import com.example.projectcapstone.network.response.CartResponse
import com.example.projectcapstone.ui.productdetail.ProductDetail


class CartFragment : Fragment() {
    private lateinit var rv_cart: RecyclerView
    private lateinit var carts : List<CartResponse>

    private lateinit var cartViewModel: CartViewModel
    private lateinit var _binding: FragmentCartBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_cart ,container, false)
        rv_cart = view.findViewById(R.id.rv_cart)
        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        getAllCarts()
        cartViewModel.carts.observe(viewLifecycleOwner){
            carts = it!!
            showCarts(carts)
        }
//        val carts= listOf(
//            CartResponse("123123123", "123123123", "Celana", "bagus", "12322.jpg","baju", 20, "blue", 20 , false, ""),
//            CartResponse("123123123", "123123123", "Celana", "bagus", "12322.jpg","baju", 20, "blue", 20 ,false, ""),
//            CartResponse("123123123", "123123123", "Celana", "bagus", "12322.jpg","baju", 20, "blue", 20 ,false, "" ),
//            CartResponse("123123123", "123123123", "Celana", "bagus", "12322.jpg","baju", 20, "blue", 20 ,false, "")
//        )
//        showCarts(carts)
    }

    fun getAllCarts() {
        cartViewModel.fetchCarts()
    }

    fun showCarts(carts: List<CartResponse>){
        rv_cart.layoutManager = LinearLayoutManager(activity)
        val adapter = CartAdapter(carts)
        rv_cart.adapter = adapter
        adapter.setOnItemClickCallback(object : CartAdapter.OnItemClickCallback{
            override fun onItemClicked(cart: CartResponse) {
                val intent = Intent(activity, ProductDetail::class.java)
//                intent.putExtra("product", cart)
                System.out.println("getclicked")
                startActivity(intent)
            }
        })
    }
}
