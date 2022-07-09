package com.example.projectcapstone.network.response

import com.google.gson.annotations.SerializedName
data class StripeResponse(

    @field:SerializedName("id")
    val IdandKey: String,


    @field:SerializedName("client_secret")
    val client_secret: String?,

)