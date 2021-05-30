package com.example.thewitcher3wiki

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface WitcherApi {
    @Headers("Content-Type:application/json")
    @POST("postlocation")
    fun addLocation(@Body locationData : LocationInfo): Call<LocationInfo>
}