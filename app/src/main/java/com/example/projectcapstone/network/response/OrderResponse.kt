package com.example.projectcapstone.network.response

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

data class OrderResponse(

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("sellerId")
    val sellerId: String,

    @field:SerializedName("productId")
    val products: String,

    @field:SerializedName("amount")
    val amount: String,

    @field:SerializedName("address")
    val address: String
)
