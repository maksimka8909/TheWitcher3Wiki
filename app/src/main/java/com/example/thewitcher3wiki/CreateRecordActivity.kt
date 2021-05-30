package com.example.thewitcher3wiki

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_create_record.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class CreateRecordActivity : AppCompatActivity() {
    var okHttpClient = OkHttpClient()
    var locationTypes = mutableListOf<String>()
    var countries = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_record)
        var adapter = ArrayAdapter.createFromResource(this, R.array.categories,R.layout.spinner_dropdown_item)
        spCategory.adapter = adapter
        getLocationTypes()
        getCountries()
        btnCheckImage.setOnClickListener {
            Picasso.get().load(edtxtSourceImage.text.toString()).into(image)
        }
        btnCreateRecord?.setOnClickListener {
            if(edtxtName.text.isNotEmpty() &&
                edtxtDescription.text.isNotEmpty() &&
                edtxtSourceImage.text.isNotEmpty() &&
                image.drawable != null &&
                image.drawable.constantState != resources.getDrawable(R.drawable.logoimage).constantState) {
                addLocation(
                    edtxtName.text.toString(),
                    edtxtDescription.text.toString(),
                    spTypeLocation.selectedItem.toString(),
                    spCountry.selectedItem.toString(),
                    edtxtSourceImage.text.toString()
                )
            }
            else{
                Toast.makeText(this, "Ошибка, проверьте " +
                        "заполненость всех полей и " +
                        "обязательно проверьте работу ссылки изображения", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun addLocation(nameLocation:String, locationDescription:String,typeLocation:String,locationCountry:String,image:String){
        val apiService = WitcherApiService()
        val locationInfo = LocationInfo(
            name = nameLocation,
            description = locationDescription,
            typeLocation =  typeLocation,
            country = locationCountry,
            image = image
        )
        apiService.addLocation(locationInfo){

        }
    }
    fun getLocationTypes(){
        locationTypes.clear()
        val request = Request.Builder().url("https://apiwitcher.azurewebsites.net/api/location/getlocationtypes").build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error",e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val jO = JSONArray(response!!.body()!!.string())
                if(jO!=null) {
                    var i = 0;
                    var bol = true;
                    while (bol) {
                        try {
                            val name = jO.getJSONObject(i).get("name").toString()
                            locationTypes.add(name)
                            i++
                            runOnUiThread {
                                val adp1: ArrayAdapter<String> = ArrayAdapter<String>(this@CreateRecordActivity, R.layout.spinner_dropdown_item, locationTypes)
                                spTypeLocation.adapter = adp1
                            }

                        } catch (e: Exception) {
                            bol = false
                        }
                    }
                }
                else{

                }

            }

        })
    }

    fun getCountries(){
        countries.clear()
        val request = Request.Builder().url("https://apiwitcher.azurewebsites.net/api/location/getcountries").build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error",e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val jO = JSONArray(response!!.body()!!.string())
                if(jO!=null) {
                    var i = 0;
                    var bol = true;
                    while (bol) {
                        try {
                            val name = jO.getJSONObject(i).get("name").toString()
                            countries.add(name)
                            i++
                            runOnUiThread {
                                val adp1: ArrayAdapter<String> = ArrayAdapter<String>(this@CreateRecordActivity, R.layout.spinner_dropdown_item, countries)
                                spCountry.adapter = adp1
                            }

                        } catch (e: Exception) {
                            bol = false
                        }
                    }
                }
                else{

                }

            }

        })

    }
}