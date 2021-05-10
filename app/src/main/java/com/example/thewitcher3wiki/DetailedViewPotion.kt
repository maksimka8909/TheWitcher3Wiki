package com.example.thewitcher3wiki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detailed_view.*
import kotlinx.android.synthetic.main.activity_detailed_view.imageAvatar
import kotlinx.android.synthetic.main.activity_detailed_view.txtDescription
import kotlinx.android.synthetic.main.activity_detailed_view.txtName
import kotlinx.android.synthetic.main.activity_detailed_view_beast.*
import kotlinx.android.synthetic.main.activity_detailed_view_potion.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException
import java.lang.Exception

class DetailedViewPotion : AppCompatActivity() {
    var okHttpClient = OkHttpClient()
    var ingredientName = mutableListOf<String>()
    var values = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        var res = intent.getStringExtra("Result")
        var name = intent.getStringExtra("Title")
        var URL = "https://apiwitcher.azurewebsites.net/api/$res"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_view_potion)
        getData("$URL/name/$name","https://apiwitcher.azurewebsites.net/api/potion/GetIngredientsName/$name")
    }
    fun getData(firstURL:String,secondURL:String){
        val firstRequest = okhttp3.Request.Builder().url(firstURL).build()
        okHttpClient.newCall(firstRequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error",e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val jO = JSONArray(response!!.body()!!.string())
                try {
                    val name = jO.getJSONObject(0).get("name").toString()
                    val image = jO.getJSONObject(0).get("image").toString()
                    val type = jO.getJSONObject(0).get("potionType").toString()
                    val description = jO.getJSONObject(0).get("description").toString()
                    runOnUiThread {
                        Picasso.get().load(image).into(imageAvatar)
                        txtName.text = name
                        txtTypePotion.text = "Тип зелья: $type"
                        txtDescription.text="Описание:\n$description"
                    }

                } catch (e: Exception) {
                    Toast.makeText(this@DetailedViewPotion, "Ошибка загрузки", Toast.LENGTH_SHORT).show()
                }
            }
        })
        val secondRequest = okhttp3.Request.Builder().url(secondURL).build()
        okHttpClient.newCall(secondRequest).enqueue(object : Callback {
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
                            val value = jO.getJSONObject(i).get("value").toString()
                            ingredientName.add(name)
                            values.add(value)
                            i++
                            runOnUiThread {
                                txtStructure.text = "Состав зелья:\n"
                                for (i in 0..(ingredientName.size-1)){
                                    txtStructure.append("${ingredientName[i]} - ${values[i]}X\n")
                                }
                            }

                        } catch (e: Exception) {
                            bol = false
                        }
                    }
                }
                else{
                    Toast.makeText(this@DetailedViewPotion,"По вашему запросу нет результата", Toast.LENGTH_LONG).show()
                }
            }
        })

    }
}