package com.example.projectcapstone.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.projectcapstone.network.ApiService
import com.example.projectcapstone.network.response.LoginResponse
import com.example.projectcapstone.network.response.RegisterResponse
import com.example.projectcapstone.network.response.ResultResponse

class Repository(
    private val apiService: ApiService
    ){

    fun register(name: String, email: String, pass: String): LiveData<ResultResponse<RegisterResponse>> =
        liveData {
            emit(ResultResponse.Loading)
            try {
                val response = apiService.register(name, email, pass)
                if (!response?.error!!) {
                    emit(ResultResponse.Success(response))
                } else {
                    Log.e(TAG, "Register Fail: ${response.message}")
                    emit(ResultResponse.Error(response.message.toString()))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Register Exception: ${e.message.toString()} ")
                emit(ResultResponse.Error(e.message.toString()))
            }
        }

    fun login(name: String, pass: String): LiveData<ResultResponse<LoginResponse>> =
        liveData {
            emit(ResultResponse.Loading)
            try {
                val response = apiService.login(name, pass)
                if (!response.error) {
                    emit(ResultResponse.Success(response))
                } else {
                    Log.e(TAG, "Login Fail: ${response.message}")
                    emit(ResultResponse.Error(response.message))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Login Exception: ${e.message.toString()} ")
                emit(ResultResponse.Error(e.message.toString()))
            }
        }
}

