package com.example.projectcapstone.ui.order

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.datastore.dataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.lifecycleScope
import com.example.projectcapstone.dataStore
import com.example.projectcapstone.model.UserPreference
import com.example.projectcapstone.network.ApiConfig
import com.example.projectcapstone.network.response.OrderResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.temporal.TemporalAmount
val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class OrderViewModel : ViewModel() {
        private val _isLoading = MutableLiveData<Boolean>()

        fun addOrder(userid: String, sellerId: String, productId: String, amount: String, address : String) {
            _isLoading.value = true
//            val userPref = UserPreference.getInstance(requireContex().dataStore )
//            lifecycleScope.launch {
//                userPref.getUser()
//            }
            val service = ApiConfig().getApiService().AddOrder("userPref.token", userid, sellerId, productId, amount, address )
            service.enqueue(object : Callback<OrderResponse> {
                override fun onResponse(
                    call: Call<OrderResponse>,
                    response: Response<OrderResponse>
                ){
                    System.out.println(response.isSuccessful)
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
//                            Toast.makeText(this@OrderViewModel, "order is successful", Toast.LENGTH_SHORT).show()


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
    }
