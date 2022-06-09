package com.example.projectcapstone.viewmodel

import androidx.lifecycle.ViewModel
import com.example.projectcapstone.repository.Repository


class LoginViewModel (private val repository: Repository): ViewModel()  {

    fun login(name: String, pass: String) =
        repository.login(name, pass)
}