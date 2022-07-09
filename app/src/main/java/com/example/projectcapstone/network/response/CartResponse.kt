package com.example.projectcapstone.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DeleteResponse(
//    @field:SerializedName("message")
//    val message: String,
    val message : String
)
@Parcelize
data class CartResponse(

    @field:SerializedName("_id")
    val cartId: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("productId")
    val productId: String,

    @field:SerializedName("sellerId")
    val sellerId: String,

    @field:SerializedName("img")
    val img: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("price")
    val price: String

):Parcelable