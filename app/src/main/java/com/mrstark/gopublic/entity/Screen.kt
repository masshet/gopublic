package com.mrstark.gopublic.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

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
) : Parcelable {
    constructor(parcel: Parcel?) : this(
            parcel?.readLong()!!,
            parcel?.readFloat()!!,
            parcel?.readFloat()!!,
            parcel?.readFloat()!!,
            parcel?.readString()!!,
            parcel?.readString()!!,
            parcel?.readParcelable(ImageSize::class.java.classLoader)!!,
            parcel?.readString()!!,
            parcel?.readString()!!,
            parcel?.readString()!!
    )

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<Screen> = object : Parcelable.Creator<Screen> {
            override fun createFromParcel(source: Parcel?): Screen? {
                return Screen(source)
            }

            override fun newArray(size: Int): Array<Screen?> {
                return arrayOfNulls<Screen?>(size)
            }

        }
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(id)
        dest?.writeFloat(cost)
        dest?.writeFloat(lat)
        dest?.writeFloat(lan)
        dest?.writeString(currency)
        dest?.writeString(image)
        dest?.writeParcelable(imageSize, flags)
        dest?.writeString(address)
        dest?.writeString(description)
        dest?.writeString(workTime)
    }

    override fun describeContents(): Int {
        return 0
    }


    class ImageSize(
            @SerializedName("width")
            private val width: Int,

            @SerializedName("height")
            private val height: Int
    ) : Parcelable {
        constructor(parcel: Parcel?) : this(parcel?.readInt()!!, parcel?.readInt()!!)

        companion object {
            @JvmField final val CREATOR: Parcelable.Creator<ImageSize> = object : Parcelable.Creator<ImageSize> {
                override fun createFromParcel(source: Parcel?): ImageSize? {
                    return ImageSize(source)
                }

                override fun newArray(size: Int): Array<ImageSize?> {
                    return arrayOfNulls<ImageSize?>(size)
                }

            }
        }

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeInt(width)
            dest?.writeInt(height)
        }

        override fun describeContents(): Int {
            return 0
        }
    }
}