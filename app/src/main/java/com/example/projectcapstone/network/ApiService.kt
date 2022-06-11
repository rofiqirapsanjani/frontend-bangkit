package com.example.projectcapstone.network

import com.example.projectcapstone.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("api/products")
    fun getAllProduct(): Call<List<Product>>

    @GET("api/carts/find/{userId}")
    fun getAllCart(
//        @Header("Authorization") value :String,
    ): Call<List<CartResponse>>

    @FormUrlEncoded
    @POST("api/carts/{id}")
    fun AddCart(
        @Field("userId") userId: String,
        @Field("products") products: List<String>,
    ): Call<CartResponse>

    @FormUrlEncoded
    @POST("api/carts/{id}/{id}")
    fun DeleteitemCart(): Call<CartResponse>

    @GET("api/wishlist/")
    fun getUserWishlist(): Call<List<WishListResponse>>

    @GET("api/orders/find/{id pembeli}")
    fun getAllOrder(
    ): Call<List<OrderResponse>>

    @FormUrlEncoded
    @POST("api/orders/{userId}")
    fun AddOrder(
        @Header("Authorization") value :String,
        @Field("userId") userId: String,
        @Field("sellerId") sellerId: String,
        @Field("productId") productId: String,
        @Field("amount") amount: String,
        @Field("address") address: String
    ): Call<OrderResponse>

    @FormUrlEncoded
    @POST("api/auth/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") pass: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("api/auth/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") pass: String
    ): LoginResponse

}