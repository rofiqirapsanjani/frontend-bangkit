package com.example.projectcapstone.ui.mystore

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

class MyStoreViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    private val _isLoading = MutableLiveData<Boolean>()
    private val _yourProducts = MutableLiveData<List<Product>?>()
    val yourProducts : LiveData<List<Product>?> = _yourProducts

    private val _itemProd = MutableLiveData<Product?>()
    val itemProd : LiveData<Product?> = _itemProd

    private val _item = MutableLiveData<Boolean?>()
    val item : LiveData<Boolean?> = _item

    val isLoading: LiveData<Boolean> = _isLoading

    fun getStoreProducts(access_token : String, sellerId: String) {
        _isLoading.value = true
        val service = ApiConfig().getApiService().getStoreProducts(access_token, sellerId)
        service.enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ){
                System.out.println(response.isSuccessful)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _yourProducts.value = responseBody
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

    fun addProduct(access_token: String, sellerId: String, sellerIdfield: String, title:String, desc : String, img:String, categories: List<String>, size : String, color : String, price: String){
        val service = ApiConfig().getApiService().addProductStore(access_token, sellerId, sellerIdfield, title, desc, img, categories, size, color, price)
        service.enqueue(object : Callback<Product> {
            override fun onResponse(
                call: Call<Product>,
                response: Response<Product>
            ) {
                System.out.println(response.isSuccessful)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _itemProd.value = responseBody
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    System.out.println("failed")
                }

            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun deleteStoreProduct(access_token : String, sellerId: String, productId : String ) {
        val service = ApiConfig().getApiService().deleteMyProduct(access_token, sellerId, productId)
        service.enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                System.out.println(response.isSuccessful)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _item.value = true
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
