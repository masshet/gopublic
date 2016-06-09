package com.mrstark.gopublic.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Order(
        @SerializedName("id")
        val id: Long,

        @SerializedName("user_id")
        val userId: Long,

        @SerializedName("screen_id")
        val screenId: Long,

        @SerializedName("name")
        val name: String,

        @SerializedName("status")
        val status: Int,

        @SerializedName("order_num")
        val orderNum: Long,

        @SerializedName("path")
        val path: String,

        @SerializedName("thumbnail_path")
        val thumbnailPath: String,

        @SerializedName("view_at")
        val viewAt: String,

        @SerializedName("show_time")
        val showTime: Int,

        @SerializedName("created_at")
        val createdAt: String,

        @SerializedName("updated_at")
        val updatedAt: String,

        @SerializedName("publish_at")
        val publishAt: String

) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readLong(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
    )

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<Order> = object : Parcelable.Creator<Order> {
            override fun createFromParcel(source: Parcel?): Order? {
                return Order(source!!)
            }

            override fun newArray(size: Int): Array<Order?> {
                return arrayOfNulls<Order?>(size)
            }

        }
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(id)
        dest?.writeLong(screenId)
        dest?.writeLong(userId)
        dest?.writeString(name)
        dest?.writeInt(status)
        dest?.writeLong(orderNum)
        dest?.writeString(path)
        dest?.writeString(thumbnailPath)
        dest?.writeString(viewAt)
        dest?.writeInt(showTime)
        dest?.writeString(createdAt)
        dest?.writeString(updatedAt)
        dest?.writeString(publishAt)
    }

    override fun describeContents(): Int {
        return 0
    }
}