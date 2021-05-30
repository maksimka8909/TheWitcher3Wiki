package com.example.thewitcher3wiki

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WitcherApiService {
    fun addLocation(locationData: LocationInfo, onResult:(LocationInfo?)->Unit){
        val retrofit = ServiceBuilder.buildService(WitcherApi::class.java)
        retrofit.addLocation(locationData).enqueue(
            object : Callback<LocationInfo>{
                override fun onFailure(call: Call<LocationInfo>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<LocationInfo>,
                    response: Response<LocationInfo>
                ) {
                    val addedLocation = response.body()
                    onResult(addedLocation)
                }
            }
        )
    }
}