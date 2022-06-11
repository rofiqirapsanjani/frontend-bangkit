package com.example.projectcapstone.ui.order

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.projectcapstone.databinding.ActivityOrderBinding
import com.example.projectcapstone.network.response.Product


class OrderActivity  : AppCompatActivity() {
//    val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
//    val pref = UserPreference.getInstance(dataStore)
    private lateinit var binding: ActivityOrderBinding
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var stripePayment : StripePayment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
//        lifecycleScope.launch{
//            accessToken=pref.getPreferences()[UserPreference.]
//        }
        val product : Product? = intent.getParcelableExtra("infoproduct")
        val productId =product!!.id
        val sellerId = product!!.sellerId
        val productPrice =product!!.price
        val productImg =product!!.img
        val productTitle = product!!.title
        System.out.println("product id "+productId)
        binding.prodid.text = product!!.id
//        binding.prodid.text = productPrice
//        binding.prodid.text = productImg
//        binding.prodid.text = productTitle

        val address = binding.Address.text.toString()
        binding.pay.setOnClickListener{
            orderViewModel.addOrder("dfdadf",sellerId, productId, productPrice, "address")
//            stripePayment.paymentStripe(product!!.price)
//            startActivity(Intent(this@OrderActivity, MainActivity::class.java))
        }
    }


}
