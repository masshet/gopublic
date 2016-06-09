package com.mrstark.gopublic.entity

import com.google.gson.annotations.SerializedName


data class Balance(
        @SerializedName("balance")
        val balance: String
)