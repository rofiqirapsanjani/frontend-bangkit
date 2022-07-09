package com.example.projectcapstone.repository

import android.content.Context
import com.example.projectcapstone.repository.Repository
import com.example.projectcapstone.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository(apiService)
    }
}