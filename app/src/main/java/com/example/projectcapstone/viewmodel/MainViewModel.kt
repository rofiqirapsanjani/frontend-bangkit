package com.example.projectcapstone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.projectcapstone.model.UserModel
import com.example.projectcapstone.model.UserPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel (private val pref: UserPreference) : ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}