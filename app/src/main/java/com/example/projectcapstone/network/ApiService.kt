package com.example.projectcapstone.network

import com.example.projectcapstone.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("api/products")
    fun getAllProduct(): Call<List<Product>>

    @GET("api/products/find/{sellerId}")
    fun getStoreProducts(
        @Header("token") value : String,
        @Path("sellerId") sellerId: String
    ): Call<List<Product>>

    @DELETE("api/products/{sellerId}/{productId}")
    fun deleteMyProduct(
        @Header("token") value : String,
        @Path("sellerId") sellerId: String,
        @Path("productId") productId: String
    ): Call<String>

    @POST("api/products/{sellerId}")
    fun addProductStore(
        @Header("token") value : String,
        @Path("sellerId") sellerId: String,
        @Field("sellerId") sellerfield: String,
        @Field("title") title: String,
        @Field("desc") desc: String,
        @Field("img") img: String,
        @Field("categories") categories: List<String>,
        @Field("size") size: String,
        @Field("color") color:String,
        @Field("price") price:String,
    ): Call<Product>

    @GET("api/carts/find/{userId}")
    fun getAllCart(
        @Header("token") value:String,
        @Path("userId") userId: String,
    ): Call<List<CartResponse>>

    @GET("api/products")
    fun getProductbyCategory(
        @Query("category") category : String,
    ): Call<List<Product>>

    @GET("api/orders/find/{userId}")
    fun getAllHistory(
        @Header("token") value:String,
        @Path("userId") userId: String,
    ): Call<List<OrderResponse>>

    @FormUrlEncoded
    @POST("api/carts/{userId}")
    fun AddCart(
        @Header("token") value:String,
        @Path("userId") userId: String,
        @Field("productId") productId: String,
        @Field("sellerId") sellerId: String,
        @Field("title") title:String,
        @Field("img") img:String,
        @Field("price") price :String
    ): Call<CartResponse>

    @DELETE("api/carts/{userId}/{cartId}")
    fun DeleteitemCart(
        @Header("token") value:String,
        @Path("userId") userId: String,
        @Path("cartId") cartId: String
    ):Call<String>

    @FormUrlEncoded
    @POST("api/orders/{userId}")
    fun AddOrder(
        @Header("token") value:String,
        @Path("userId") userId: String,
        @Field("sellerId") sellerId: String,
        @Field("productId") productId: String,
        @Field("amount") amount: String,
        @Field("address") address: String,
        @Field("img") img :String,
        @Field("title") title: String
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