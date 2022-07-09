package com.example.projectcapstone.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcapstone.model.UserPreference
import kotlinx.coroutines.launch

class ProfileViewModel(private val pref: UserPreference)  : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }
    val text: LiveData<String> = _text

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

}