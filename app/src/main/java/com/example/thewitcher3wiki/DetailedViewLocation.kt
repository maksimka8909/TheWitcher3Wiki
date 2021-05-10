package com.example.thewitcher3wiki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detailed_view.*
import kotlinx.android.synthetic.main.activity_detailed_view.imageAvatar
import kotlinx.android.synthetic.main.activity_detailed_view.txtActivity
import kotlinx.android.synthetic.main.activity_detailed_view.txtDescription
import kotlinx.android.synthetic.main.activity_detailed_view.txtName
import kotlinx.android.synthetic.main.activity_detailed_view_location.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException
import java.lang.Exception

class DetailedViewLocation : AppCompatActivity() {
    var okHttpClient = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        var res = intent.getStringExtra("Result")
        var name = intent.getStringExtra("Title")
        var URL = "https://apiwitcher.azurewebsites.net/api/$res"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_view_location)
        Request("$URL/name/$name")
    }
    fun Request(URL:String){
        val request = okhttp3.Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error",e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val jO = JSONArray(response!!.body()!!.string())
                try {
                    val name = jO.getJSONObject(0).get("name").toString()
                    val image = jO.getJSONObject(0).get("image").toString()
                    val typeLocation = jO.getJSONObject(0).get("typeLocation").toString()
                    val country = jO.getJSONObject(0).get("country").toString()
                    val description = jO.getJSONObject(0).get("description").toString()
                    runOnUiThread {
                        Picasso.get().load(image).into(imageAvatar)
                        txtName.text = name
                        txtTypeLocation.text = "Тип локации: $typeLocation"
                        txtCountry.text="Страна: $country"
                        txtDescription.text="Описание:\n$description"
                    }

                } catch (e: Exception) {

                    Toast.makeText(this@DetailedViewLocation, "Ошибка загрузки", Toast.LENGTH_SHORT).show()
                }



            }

        })
    }
}