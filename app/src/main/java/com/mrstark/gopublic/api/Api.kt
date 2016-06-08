package com.mrstark.gopublic.api

import com.google.gson.JsonObject
import com.mrstark.gopublic.entity.Order
import com.mrstark.gopublic.entity.Screen
import com.mrstark.gopublic.entity.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit.mime.TypedFile
import retrofit.mime.TypedString
import retrofit2.Call
import retrofit2.http.*
import java.io.File

/**
 * Created by mrstark on 5/14/16.
 */
interface Api {

    @GET("all-screen")
    fun getAllScreens(): Call<List<Screen>>

    @Headers(
            "X-Auth-Service-Provider: https://api.digits.com/1.1/sdk/account.json",
            "APPLICATION-SECRET: c2QOp5ig5m19U3wh0LVrRql71oxL39sb",
            "APPLICATION-NAME: android"
    )

    @GET("login")
    fun login(@Header("X-Verify-Credentials-Authorization") credentials: String): Call<User>

    @Headers(
            "X-Auth-Service-Provider: https://api.digits.com/1.1/sdk/account.json",
            "APPLICATION-SECRET: c2QOp5ig5m19U3wh0LVrRql71oxL39sb",
            "APPLICATION-NAME: android"
    )
    @GET("balance")
    fun balance(@Header("X-Verify-Credentials-Authorization") credentials: String): Call<JsonObject>

    @Headers(
            "X-Auth-Service-Provider: https://api.digits.com/1.1/sdk/account.json",
            "APPLICATION-SECRET: c2QOp5ig5m19U3wh0LVrRql71oxL39sb",
            "APPLICATION-NAME: android"
    )
    @GET("orders")
    fun getOrders(@Header("X-Verify-Credentials-Authorization") credentials: String): Call<List<Order>>

}
