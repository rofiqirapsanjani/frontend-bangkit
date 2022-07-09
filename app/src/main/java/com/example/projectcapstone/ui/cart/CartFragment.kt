package com.example.projectcapstone.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcapstone.MainActivity
import com.example.projectcapstone.R
import com.example.projectcapstone.adapter.CartAdapter
import com.example.projectcapstone.databinding.ActivityProductDetailBinding
import com.example.projectcapstone.databinding.FragmentCartBinding
import com.example.projectcapstone.model.UserPreference
import com.example.projectcapstone.network.response.CartResponse
import com.example.projectcapstone.network.response.Product
import com.example.projectcapstone.ui.ProductDetail
import com.example.projectcapstone.ui.order.OrderActivity
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class CartFragment : Fragment() {
    private lateinit var rv_cart: RecyclerView
    private lateinit var carts : List<CartResponse>
    private lateinit var userId : String
    private lateinit var access_token : String
    private lateinit var cartViewModel: CartViewModel
    private lateinit var _binding: FragmentCartBinding
    private var cartBool: Boolean = false

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
        val userPref = UserPreference.getInstance(requireActivity().dataStore)

        lifecycleScope.launch {
            access_token= userPref.getPreferences()[UserPreference.TOKEN_KEY].toString()
            access_token = "Bearer "+ access_token
            userId = userPref.getPreferences()[UserPreference.USERID_KEY].toString()
        }
        getAllCarts(access_token, userId)
        cartViewModel.carts.observe(viewLifecycleOwner){
            carts = it!!
            CartActivity(carts)
        }
    }

    fun getAllCarts(access_token: String, userId:String) {
        cartViewModel.getAllCarts(access_token, userId)
    }
    fun CartActivity(carts: List<CartResponse>){
        rv_cart.layoutManager = LinearLayoutManager(activity)
        val adapter = CartAdapter(carts, access_token)
        rv_cart.adapter = adapter
        adapter.setOnItemClickCallback(object : CartAdapter.OnItemClickCallback{
            override fun onItemClicked(cart: CartResponse) {
                val intent = Intent(activity, OrderActivity::class.java)
                intent.putExtra("cartproduct", cart)
                System.out.println("showcarts")
                startActivity(intent)
            }
            override fun onLongItemClicked(cart: CartResponse) {
                cartViewModel.DeleteCart(access_token, userId, cart.cartId)
                cartViewModel.cart.observe(viewLifecycleOwner){
                    cartBool = it!!
                    if (cartBool){
                        getAllCarts(access_token, userId)
                    }
                }
            }
        })
    }
}


