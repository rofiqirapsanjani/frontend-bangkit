package com.example.projectcapstone.ui.mystore

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectcapstone.network.ApiConfig
import com.example.projectcapstone.network.response.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyStoreViewModel {

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
    companion object {
        private const val TAG = "MyStoreViewModel"
        private const val SUCCESS = "success"
    }
}
