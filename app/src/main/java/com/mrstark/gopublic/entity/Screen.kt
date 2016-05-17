package com.mrstark.gopublic.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by mrstark on 5/9/16.
 */
data class Screen(

        @SerializedName("id")
        val id: Long,

        @SerializedName("cost")
        val cost: Float,

        @SerializedName("lat")
        val lat: Float,

        @SerializedName("lan")
        val lan: Float,

        @SerializedName("currency")
        val currency: String,

        @SerializedName("image_path")
        val image: String,

        @SerializedName("image_size")
        val imageSize: Screen.ImageSize,

        @SerializedName("address")
        val address: String,

        @SerializedName("description")
        val description: String,

        @SerializedName("work_time")
        val workTime: String
) {

    class ImageSize(
            @SerializedName("width")
            private val width: Int,

            @SerializedName("height")
            private val height: Int
    )
}