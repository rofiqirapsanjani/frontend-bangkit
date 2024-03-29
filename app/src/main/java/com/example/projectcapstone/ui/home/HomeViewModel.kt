package com.example.projectcapstone.ui.home

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectcapstone.network.ApiConfig
import com.example.projectcapstone.network.response.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    private val _isLoading = MutableLiveData<Boolean>()
    private val _products = MutableLiveData<List<Product>?>()
    val products : LiveData<List<Product>?> = _products
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchProducts() {
        _isLoading.value = true
        val service = ApiConfig().getApiService().getAllProduct()
        service.enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ){
                System.out.println(response.isSuccessful)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _products.value = responseBody
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    System.out.println("failed")
                }

            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}" )
            }
        })
    }

    fun getProductbyCategory(category: String){
        val service = ApiConfig().getApiService().getProductbyCategory(category)
        service.enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ){
                System.out.println(response.isSuccessful)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _products.value = responseBody
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    System.out.println("failed")
                }

            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}" )
            }
        })
    }
    companion object {
        private const val TAG = "HomeViewModel"
        private const val SUCCESS = "success"
    }
}