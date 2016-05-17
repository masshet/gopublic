package com.mrstark.gopublic.api

import com.mrstark.gopublic.entity.Screen

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by mrstark on 5/14/16.
 */
interface Api {

    @GET("api/all-screen")
    fun getAllScreens(): Call<List<Screen>>
}
