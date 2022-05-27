package com.example.projectcapstone.viewmodel

import androidx.lifecycle.*
import com.example.projectcapstone.model.UserModel
import com.example.projectcapstone.model.UserPreference
import kotlinx.coroutines.launch

class LoginViewModel (private val pref: UserPreference) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }
}