package com.example.projectcapstone.network.response

import com.google.gson.annotations.SerializedName
data class CartResponse(

    @field:SerializedName("_id")
    val cartId: String,

    @field:SerializedName("userId")
    val userId: String,

//    @field:SerializedName("productId")
//    val productId: String

)