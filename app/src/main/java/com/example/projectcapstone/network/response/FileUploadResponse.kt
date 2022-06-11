package com.example.projectcapstone.network.response

import com.google.gson.annotations.SerializedName

data class FileUploadResponse(

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)
