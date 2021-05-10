package com.example.thewitcher3wiki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detailed_view.*
import kotlinx.android.synthetic.main.activity_list.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException
import java.lang.Exception

class DetailedViewActivity : AppCompatActivity() {
    var okHttpClient = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        var res = intent.getStringExtra("Result")
        var name = intent.getStringExtra("Title")
        var URL = "https://apiwitcher.azurewebsites.net/api/$res"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_view)
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
                            val race = jO.getJSONObject(0).get("race").toString()
                            val activity = jO.getJSONObject(0).get("activity").toString()
                            val description = jO.getJSONObject(0).get("description").toString()
                            runOnUiThread {
                                Picasso.get().load(image).into(imageAvatar)
                                txtName.text = name
                                txtRace.text = "Раса: $race"
                                txtActivity.text="Деятельность: $activity"
                                txtDescription.text="Описание:\n$description"
                            }

                        } catch (e: Exception) {
                            Toast.makeText(this@DetailedViewActivity, "Ошибка загрузки", Toast.LENGTH_SHORT).show()
                        }



            }

        })
    }
}