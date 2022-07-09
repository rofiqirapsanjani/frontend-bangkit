package com.example.projectcapstone.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.size
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.projectcapstone.MainActivity
import com.example.projectcapstone.R
import com.example.projectcapstone.authentication.register.RegisterActivity
import com.example.projectcapstone.databinding.ActivityProductDetailBinding
import com.example.projectcapstone.model.UserPreference
import com.example.projectcapstone.network.response.CartResponse
import com.example.projectcapstone.network.response.Product
import com.example.projectcapstone.ui.cart.CartFragment
import com.example.projectcapstone.ui.cart.CartViewModel
import com.example.projectcapstone.ui.order.OrderActivity
import kotlinx.coroutines.launch
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class ProductDetail : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var userId : String
    private lateinit var access_token : String
    private var cart : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        val product : Product? = intent.getParcelableExtra("product")

        val infoProduct = product!!
        val productId = infoProduct.id!!
        val productTitle = infoProduct.title
        val productImg = infoProduct.img
        val productPrice = infoProduct.price
        val sellerProduct = infoProduct.sellerId
        val productDesc = infoProduct.description
        val productSize = infoProduct.size

        binding.apply {
            binding.idName.text = productTitle
            binding.price.text = productPrice
            binding.description.text = productDesc
            binding.sizeno.text = productSize
            Glide.with(this@ProductDetail)
                .load(product.img)
                .into(ivDetailProduct)
        }

        val userPref = UserPreference.getInstance(dataStore)
        lifecycleScope.launch {
            access_token= userPref.getPreferences()[UserPreference.TOKEN_KEY].toString()
            access_token = "Bearer "+ access_token
            userId = userPref.getPreferences()[UserPreference.USERID_KEY].toString()
        }

        binding.cart.setOnClickListener{
            cartViewModel.AddCart(access_token, userId, productId, sellerProduct,productTitle, productImg, productPrice )
            cartViewModel.cart.observe(this){
                cart = it!!
                if(cart) {
                    showAlertDialog(cart, getString(R.string.tambah_keranjang_ok))
                }
            }

        }

        binding.order.setOnClickListener{
            val intent = Intent(this@ProductDetail, OrderActivity::class.java)
            intent.putExtra("infoproduct", infoProduct)
            startActivity(intent)
        }
    }
    private fun showAlertDialog(param: Boolean, message: String) {
        if (param) {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.information))
                setMessage(message)
                setPositiveButton(getString(R.string.ok)) { _, _ ->
                }
                create()
                show()
            }
        } else {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.information))
                setMessage(message)
                create()
                show()

            }
        }
    }




}