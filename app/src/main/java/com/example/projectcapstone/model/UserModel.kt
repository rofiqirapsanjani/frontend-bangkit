package com.example.projectcapstone.model

data class UserModel(
    val name: String,
    val email: String,
    val password: String,
    val userId: String,
    val token: String,
    val isLogin: Boolean
)
