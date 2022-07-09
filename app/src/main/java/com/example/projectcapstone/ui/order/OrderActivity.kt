package com.example.projectcapstone.ui.order

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.projectcapstone.MainActivity
import com.example.projectcapstone.databinding.ActivityOrderBinding
import com.example.projectcapstone.model.UserPreference
import com.example.projectcapstone.network.ApiConfig
import com.example.projectcapstone.network.response.CartResponse
import com.example.projectcapstone.network.response.Product
import com.example.projectcapstone.network.response.StripeResponse
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class OrderActivity  : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var access_token : String
    private lateinit var userId : String
    private lateinit var nameuser : String
    private lateinit var sellerId : String
    private lateinit var productPrice :String
    private var prodcartId :String = ""
    private var productId :String = ""
    private lateinit var productImg :String
    private lateinit var productTitle :String

    var SECRET_KEY =
        "sk_test_51L5k3GANlkH3ybbSYtBaVQ2POPB7W6dT7zDBei8UsJwYFYNkLGY11BumUkvcYY7cTf2TjtHOoH9u0Qyp8Z61Q35900FAvGsU8x"
    var PUBLISH_KEY =
        "pk_test_51L5k3GANlkH3ybbSXUdJ6YzdbSUwrJyuz3HzqtRjLDtpODGBxqWw1s6cWrZvfjRNwOePjzFZFMRXluzJarFRLB4D00QfemV6Ir"
    lateinit var paymentSheet: PaymentSheet
    lateinit var CustomerID: String
    lateinit var EphemeralKey: String
    lateinit var ClientSecret: String
    lateinit var transaction: String
    var currency = "idr"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PaymentConfiguration.init(this, PUBLISH_KEY)

        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        val userPref = UserPreference.getInstance(dataStore)

        lifecycleScope.launch {
            access_token= userPref.getPreferences()[UserPreference.TOKEN_KEY].toString()
            access_token = "Bearer "+ access_token
            userId = userPref.getPreferences()[UserPreference.USERID_KEY].toString()
            nameuser = userPref.getPreferences()[UserPreference.NAME_KEY].toString()
        }
        val product : Product? = intent.getParcelableExtra("infoproduct")
        val cart : CartResponse? = intent.getParcelableExtra("cartproduct")

        if (product != null) {
            productId = product!!.id
            sellerId = product!!.sellerId
            productPrice = product!!.price
            productImg = product!!.img
            productTitle = product!!.title
            binding.total.text = "Amount: " + productPrice
            binding.tvName.text = "Nama: " + nameuser
        }else if (cart != null) {
            productId = cart!!.productId
            sellerId = cart!!.sellerId
            productPrice = cart!!.price
            productImg = cart!!.img
            productTitle = cart!!.title
            binding.total.text = "Amount: " + productPrice
            binding.tvName.text = "Nama: " + nameuser
        }

        binding.checkboxReguler.setOnClickListener{
            if(binding.checkboxReguler.isChecked){
                binding.checkboxInstant.isChecked = false
                binding.checkboxSameday.isChecked = false
            }
        }
        binding.checkboxSameday.setOnClickListener{
            if( binding.checkboxSameday.isChecked){
                binding.checkboxReguler.isChecked = false
                binding.checkboxInstant.isChecked = false
            }
        }
        binding.checkboxInstant.setOnClickListener{
            if( binding.checkboxInstant.isChecked){
                binding.checkboxReguler.isChecked = false
                binding.checkboxSameday.isChecked = false
            }
        }
        binding.Pay.setOnClickListener{
            val address = "${binding.etAddress.text}"
            System.out.println("address " + address)
            paymentStripe(productPrice)
            orderViewModel.addOrder(access_token, userId, sellerId, productId, productPrice, address, productImg, productTitle)

        }
    }

    fun paymentStripe(amount: String) {
        transaction = amount + "00"
        getcustomerID()

    }

    private fun getcustomerID() {
        val service = ApiConfig().getApiStripe().getcustomerID("Bearer " + SECRET_KEY)
        service.enqueue(object : Callback<StripeResponse> {
            override fun onResponse(
                call: Call<StripeResponse>,
                response: Response<StripeResponse>
            ) {
                System.out.println(response.isSuccessful)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        CustomerID = responseBody.IdandKey
                        System.out.println(CustomerID)

                        getEphemeralKey(CustomerID)

                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    System.out.println("failed")

                }
            }

            override fun onFailure(call: Call<StripeResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")

            }

        })
    }

    private fun getEphemeralKey(customerId: String?) {
        val service = ApiConfig().getApiStripe().getEphemeralKey("Bearer " + SECRET_KEY, "2020-08-27", customerId)
        service.enqueue(object : Callback<StripeResponse> {
            override fun onResponse(
                call: Call<StripeResponse>,
                response: Response<StripeResponse>
            ) {
                System.out.println(response.isSuccessful)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        EphemeralKey = responseBody.IdandKey
                        System.out.println(EphemeralKey)

                        getClientSecret(CustomerID, EphemeralKey)
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    System.out.println("failed")

                }
            }

            override fun onFailure(call: Call<StripeResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")

            }

        })
    }

    private fun getClientSecret(customerID: String?, ephemeralKey: String?) {
        val service = ApiConfig().getApiStripe().getClientSecret("Bearer " + SECRET_KEY,  customerID, transaction, currency, automatic_payment_methods = true)
        service.enqueue(object : Callback<StripeResponse> {
            override fun onResponse(
                call: Call<StripeResponse>,
                response: Response<StripeResponse>
            ) {
                System.out.println(response.isSuccessful)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        ClientSecret = responseBody.client_secret.toString()
                        System.out.println(ClientSecret)
                        PaymentFlow()
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    System.out.println("failed")

                }
            }

            override fun onFailure(call: Call<StripeResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")

            }

        })

    }

    private fun PaymentFlow() {
        System.out.println("payment sheet")
        paymentSheet.presentWithPaymentIntent(
            ClientSecret, PaymentSheet.Configuration(
                "thriftee",
                PaymentSheet.CustomerConfiguration(
                    CustomerID, EphemeralKey)
            )
        )
    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when (paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                print("Canceled")
                startActivity(Intent(this@OrderActivity, MainActivity::class.java))

            }
            is PaymentSheetResult.Failed -> {
                print("Error: ${paymentSheetResult.error}")
                startActivity(Intent(this@OrderActivity, MainActivity::class.java))

            }
            is PaymentSheetResult.Completed -> {
                startActivity(Intent(this@OrderActivity, MainActivity::class.java))
            }
        }
    }

    fun onCheckboxClicked(view: View) {}
}

