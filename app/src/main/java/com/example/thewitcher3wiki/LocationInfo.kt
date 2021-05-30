package com.example.thewitcher3wiki

import android.content.ClipDescription
import com.google.gson.annotations.SerializedName

data class LocationInfo(
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("typeLocation") val typeLocation: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("image") val image: String?
)