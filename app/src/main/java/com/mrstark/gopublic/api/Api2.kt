package com.mrstark.gopublic.api

import org.json.JSONObject
import retrofit.Callback
import retrofit.http.*
import retrofit.mime.TypedFile
import retrofit.mime.TypedString

/**
 * Created by mrstark on 6/7/16.
 */
interface Api2 {

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