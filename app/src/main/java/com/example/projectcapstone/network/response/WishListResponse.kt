package com.example.projectcapstone.network.response

import com.google.gson.annotations.SerializedName
data class WishListResponse(

    @field:SerializedName("_id")
    val productId: String,

    @field:SerializedName("sellerId")
    val sellerId: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("desc")
    val desc: String,

    @field:SerializedName("img")
    val imgurl: String,

    @field:SerializedName("categories")
    val categories: String,

    @field:SerializedName("size")
    val size: Int,

    @field:SerializedName("color")
    val color: String,

    @field:SerializedName("price")
    val price: Int,

//    @field:SerializedName("like")
//    val like: Boolean,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)