package com.example.projectcapstone.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcapstone.helper.Helper
import com.example.projectcapstone.model.UserModel
import com.example.projectcapstone.model.UserPreference
import com.example.projectcapstone.network.ApiConfig
import com.example.projectcapstone.network.response.LoginResponse
import com.example.projectcapstone.repository.Repository
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel (private val repository: Repository): ViewModel()  {

    fun login(email: String, pass: String) =
        repository.login(email, pass)
}