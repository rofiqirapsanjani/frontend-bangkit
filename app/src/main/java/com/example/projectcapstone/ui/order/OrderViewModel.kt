package com.example.projectcapstone.ui.order

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectcapstone.network.ApiConfig
import com.example.projectcapstone.network.response.OrderResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderViewModel : ViewModel() {
    private  var thisconfirmation : Boolean = false

    private val _text = MutableLiveData<String>().apply {
            value = "This is notifications Fragment"
        }
        val text: LiveData<String> = _text
        private val _isLoading = MutableLiveData<Boolean>()
//        private lateinit var stripePayment : StripePayment
        private val _responseorder = MutableLiveData<OrderResponse?>()
        val responseorder : LiveData<OrderResponse?> = _responseorder

        fun addOrder(access_token : String,userid: String, sellerId: String, productId: String, amount: String, address : String, img :String, title:String) {
            _isLoading.value = true
            val service = ApiConfig().getApiService().AddOrder(access_token,userid, sellerId, productId, amount, address, img, title )
            service.enqueue(object : Callback<OrderResponse> {
                override fun onResponse(
                    call: Call<OrderResponse>,
                    response: Response<OrderResponse>
                ){
                    System.out.println(response.isSuccessful)
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            System.out.println(responseBody)
                            _responseorder.value = responseBody
                            thisconfirmation = true
                        }
                    } else {
                        Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                        System.out.println("failed")

                    }

                }

                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    Log.e(ContentValues.TAG, "onFailure: not connected ")

                }
            })
        }

        fun getConfirmation(): Boolean {
            return thisconfirmation
        }
    }

