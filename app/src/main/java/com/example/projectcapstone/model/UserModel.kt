package com.example.projectcapstone.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    val username: String,
    val email: String,
    val password: String,
    val isAdmin: Boolean,
    val _id: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int

)
