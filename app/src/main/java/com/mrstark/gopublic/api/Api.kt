package com.mrstark.gopublic.api

import com.google.gson.JsonObject
import com.mrstark.gopublic.entity.Balance
import com.mrstark.gopublic.entity.Order
import com.mrstark.gopublic.entity.Screen
import com.mrstark.gopublic.entity.User
import org.json.JSONObject
import retrofit.Callback
import retrofit.client.Response
import retrofit.http.*
import retrofit.mime.TypedFile
import retrofit.mime.TypedString

/**
 * Created by mrstark on 6/7/16.
 */
interface Api {

    @GET("/all-screen")
    fun getAllScreens(callback: Callback<List<Screen>>)

    @Headers(
            "X-Auth-Service-Provider: https://api.digits.com/1.1/sdk/account.json",
            "APPLICATION-SECRET: c2QOp5ig5m19U3wh0LVrRql71oxL39sb",
            "APPLICATION-NAME: android"
    )
    @GET("/login")
    fun login(@Header("X-Verify-Credentials-Authorization") credentials: String,
              callback: Callback<User>)

    @Headers(
            "X-Auth-Service-Provider: https://api.digits.com/1.1/sdk/account.json",
            "APPLICATION-SECRET: c2QOp5ig5m19U3wh0LVrRql71oxL39sb",
            "APPLICATION-NAME: android"
    )
    @GET("/balance")
    fun balance(@Header("X-Verify-Credentials-Authorization") credentials: String,
                callback: Callback<Balance>)

    @Headers(
            "X-Auth-Service-Provider: https://api.digits.com/1.1/sdk/account.json",
            "APPLICATION-SECRET: c2QOp5ig5m19U3wh0LVrRql71oxL39sb",
            "APPLICATION-NAME: android"
    )
    @GET("/orders")
    fun getOrders(@Header("X-Verify-Credentials-Authorization") credentials: String,
                  callback: Callback<List<Order>>)

    @Headers(
            "X-Auth-Service-Provider: https://api.digits.com/1.1/sdk/account.json",
            "APPLICATION-SECRET: c2QOp5ig5m19U3wh0LVrRql71oxL39sb",
            "APPLICATION-NAME: android"
    )
    @Multipart
    @POST("/upload-image")
    fun upload(@Header("X-Verify-Credentials-Authorization") credentials: String,
               @Part("photo") photo: TypedFile,
               @Part("show_time") showTime: TypedString,
               @Part("screen_id") screenId: TypedString,
               @Part("showNow") showNow: TypedString,
               @Part("date") date: TypedString,
               @Part("time") time: TypedString,
               @Part("accept") accept: TypedString,
               callback: Callback<JSONObject>)
}