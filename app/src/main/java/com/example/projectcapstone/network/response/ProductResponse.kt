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

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("harga")
    val harga: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("lon")
    val lon: Double?,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("lat")
    val lat: Double?

) : Parcelable
