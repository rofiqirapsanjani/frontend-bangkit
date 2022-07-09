package com.example.projectcapstone.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ProductResponse(
    @field:SerializedName("listProduct")
    val product: List<Product>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

@Parcelize
data class Product(

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("sellerId")
    val sellerId: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("desc")
    val description: String,

    @field:SerializedName("img")
    val img: String,

    @field:SerializedName("categories")
    val categories: List<String>,

    @field:SerializedName("size")
    val size: String,

    @field:SerializedName("color")
    val color: String,

    @field:SerializedName("price")
    val price: String,

):Parcelable

