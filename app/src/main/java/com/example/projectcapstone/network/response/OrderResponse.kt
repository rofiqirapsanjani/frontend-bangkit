package com.example.projectcapstone.network.response

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

data class OrderResponse(

    @field:SerializedName("_id")
    val orderId: String,

    @field:SerializedName("sellerId")
    val sellerId: String,

    @field:SerializedName("productId")
    val products: String,

    @field:SerializedName("amount")
    val amount: String,

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("img")
    val img: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("status")
    val status: String
)
