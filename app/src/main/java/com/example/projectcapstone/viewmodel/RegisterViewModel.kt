package com.example.projectcapstone.viewmodel

import android.util.Log
import androidx.appcompat.widget.ThemedSpinnerAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectcapstone.helper.Helper
import com.example.projectcapstone.network.ApiConfig
import com.example.projectcapstone.network.response.RegisterResponse
import com.example.projectcapstone.repository.Repository
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(val repository: Repository)  : ViewModel() {
    fun register(name: String, email: String, pass: String) =
        repository.register(name, email, pass)

}