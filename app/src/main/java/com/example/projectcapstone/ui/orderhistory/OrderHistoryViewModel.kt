package com.example.projectcapstone.ui.orderhistory

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

class OrderHistoryViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
    private val _isLoading = MutableLiveData<Boolean>()
    private val _hist = MutableLiveData<List<OrderResponse>?>()
    val hist : LiveData<List<OrderResponse>?> = _hist

    fun getAllhistory(access_token : String, userId:String){
        _isLoading.value = true
        val service = ApiConfig().getApiService().getAllHistory(access_token, userId)
        service.enqueue(object : Callback<List<OrderResponse>> {
            override fun onResponse(
                call: Call<List<OrderResponse>>,
                response: Response<List<OrderResponse>>
            ){
                System.out.println(response.isSuccessful)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _hist.value = responseBody
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    System.out.println("failed")

                }

            }

            override fun onFailure(call: Call<List<OrderResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: not connected ")

            }
        })
    }

}