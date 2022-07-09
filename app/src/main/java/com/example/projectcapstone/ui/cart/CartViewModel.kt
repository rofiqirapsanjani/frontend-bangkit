package com.example.projectcapstone.ui.cart

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectcapstone.network.ApiConfig
import com.example.projectcapstone.network.response.CartResponse
import com.example.projectcapstone.network.response.DeleteResponse
import com.example.projectcapstone.network.response.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.Path

class CartViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    private var addcart : Boolean = false
    val text: LiveData<String> = _text
    private val _isLoading = MutableLiveData<Boolean>()
    private val _carts = MutableLiveData<List<CartResponse>?>()
    val carts : LiveData<List<CartResponse>?> = _carts

    private val _product = MutableLiveData<Product?>()
    val product : LiveData<Product?> = _product

    private val _cart = MutableLiveData<Boolean?>()
    val cart : LiveData<Boolean?> = _cart

    fun getAllCarts(access_token : String, userId:String){
        _isLoading.value = true
        val service = ApiConfig().getApiService().getAllCart(access_token, userId)
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

    fun AddCart(token :String, userId: String, productId : String, sellerId:String, title:String,  img:String, price:String){
        _isLoading.value = true
        val service = ApiConfig().getApiService().AddCart(token, userId, productId, sellerId, title,img, price)
        service.enqueue(object : Callback<CartResponse> {
            override fun onResponse(
                call: Call<CartResponse>,
                response: Response<CartResponse>
            ){
                System.out.println(response.isSuccessful)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _cart.value = true
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    System.out.println("failed")

                }

            }

            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: not connected ")
            }
        })
    }

    fun DeleteCart(token :String, userId: String, cartId : String){
        _isLoading.value = true
        System.out.println(token + "\n" + userId + "\n" + cartId)
        val service = ApiConfig().getApiService().DeleteitemCart(token,userId, cartId)
        service.enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ){
                System.out.println(response.isSuccessful)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _cart.value = true
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    System.out.println("failed")

                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}