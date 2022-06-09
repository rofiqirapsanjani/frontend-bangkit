package com.example.projectcapstone.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("_id")
    val userId: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val name: String,

    @field:SerializedName("accessToken")
    val token: String,

    @field:SerializedName("isAdmin")
    val isAdmin: Boolean,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)
