package com.example.projectcapstone.network

import com.example.projectcapstone.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiStripe {

    @POST("customers")
    fun getcustomerID(
        @Header("Authorization") value:String
    ): Call<StripeResponse>

    @FormUrlEncoded
    @POST("ephemeral_keys")
    fun getEphemeralKey(
        @Header("Authorization") value:String,
        @Header("Stripe-Version") stripeVersion: String,
        @Field("customer") customer: String?,
    ): Call<StripeResponse>

    @FormUrlEncoded
    @POST("payment_intents")
    fun getClientSecret(
        @Header("Authorization") value:String,
        @Field("customer") customer: String?,
        @Field("amount") amount: String,
        @Field("currency") currency: String,
        @Field("automatic_payment_methods[enabled]") automatic_payment_methods: Boolean
    ): Call<StripeResponse>

}