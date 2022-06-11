package com.example.projectcapstone.ui.wishlist

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectcapstone.network.ApiConfig
import com.example.projectcapstone.network.response.WishListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WishlistViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val _isLoading = MutableLiveData<Boolean>()
    private lateinit var wishtlist: ArrayList<WishListResponse>

    val isLoading: LiveData<Boolean> = _isLoading
    fun fetchWishes() {
        _isLoading.value = true
        val service = ApiConfig().getApiService().getUserWishlist()
        service.enqueue(object : Callback<List<WishListResponse>> {
            override fun onResponse(
                call: Call<List<WishListResponse>>,
                response: Response<List<WishListResponse>>
            ){
                System.out.println(response.isSuccessful)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        wishtlist = responseBody as ArrayList<WishListResponse>
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    System.out.println("failed")

                }

            }

            override fun onFailure(call: Call<List<WishListResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: not connected ")

            }
        })
    }
    fun getWishes(): ArrayList<WishListResponse> {
        return wishtlist
    }
    companion object {
        private const val TAG = "HomeViewModel"
        private const val SUCCESS = "success"
    }

}