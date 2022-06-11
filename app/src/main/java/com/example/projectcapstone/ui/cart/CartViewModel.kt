package com.example.projectcapstone.ui.cart

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectcapstone.network.ApiConfig
import com.example.projectcapstone.network.response.CartResponse
import com.example.projectcapstone.network.response.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
    private val _isLoading = MutableLiveData<Boolean>()
    private val _carts = MutableLiveData<List<CartResponse>?>()
    val carts : LiveData<List<CartResponse>?> = _carts

    fun fetchCarts() {
        _isLoading.value = true
        val service = ApiConfig().getApiService().getAllCart()
        service.enqueue(object : Callback<List<CartResponse>> {
            override fun onResponse(
                call: Call<List<CartResponse>>,
                response: Response<List<CartResponse>>
            ){
                System.out.println(response.isSuccessful)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _carts.value = responseBody
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    System.out.println("failed")

                }

            }

            override fun onFailure(call: Call<List<CartResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: not connected ")

            }
        })
    }
//    fun getCarts(): ArrayList<CartResponse> {
//        return carts
//    }
}
