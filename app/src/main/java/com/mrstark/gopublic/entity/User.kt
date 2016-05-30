package com.mrstark.gopublic.entity

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("id")
        val id: Long,

        @SerializedName("phone_number")
        val phoneNumber: String,

        @SerializedName("name")
        val name: String,

        @SerializedName("lname")
        val lname: String,

        @SerializedName("email")
        val email: String,

        @SerializedName("role")
        val role: Int,

        @SerializedName("createdAt")
        val createdAt: String,

        @SerializedName("updatedAt")
        val updatedAt: String
)
