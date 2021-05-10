package com.example.thewitcher3wiki

import android.app.Activity
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class ListActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener {

    var okHttpClient = OkHttpClient()
    var titles = mutableListOf<String>()
    var imagesLinks = mutableListOf<String>()
    var res:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        res = intent.getStringExtra("Result")

        var URL = "https://apiwitcher.azurewebsites.net/api/$res"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val recyclerView: RecyclerView = findViewById(R.id.listRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        Request(URL,recyclerView)
        btnCancel.setOnClickListener{
            hideKeyboard()
            edtxtSearch.text.clear()
            Request(URL,recyclerView)
        }
        btnSearch.setOnClickListener {
            hideKeyboard()
            if(edtxtSearch.text.isEmpty())
            {
                Request(URL,recyclerView)
            }
            else {
                Request(URL + "/name/${edtxtSearch.text.toString()}", recyclerView)
            }
        }
    }
    fun hideKeyboard(){
        val view = this.currentFocus
        if(view!=null){
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(view.windowToken, 0 )
        }
        else{
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
    }
    override fun onItemClick(position: Int) {
        val titl = titles[position]
        Toast.makeText(this, "Вы выбрали статью $titl ", Toast.LENGTH_SHORT).show()
        if(res=="character") {
            val intent = Intent(this, DetailedViewActivity::class.java)
            intent.putExtra("Title", titles[position])
            intent.putExtra("Result", res)
            startActivity(intent)
        }
        if(res=="location"){
            val intent = Intent(this, DetailedViewLocation::class.java)
            intent.putExtra("Title", titles[position])
            intent.putExtra("Result", res)
            startActivity(intent)
        }
        if(res=="beast"){
            val intent = Intent(this, DetailedViewBeast::class.java)
            intent.putExtra("Title", titles[position])
            intent.putExtra("Result", res)
            startActivity(intent)
        }
        if(res=="potion/elixirs"||res=="potion/decotions"){
            val intent = Intent(this, DetailedViewPotion::class.java)
            intent.putExtra("Title", titles[position])
            intent.putExtra("Result", res)
            startActivity(intent)
        }
        if(res=="equipment/armor"||res=="equipment/sword"){
            val intent = Intent(this, DetailedViewEquipment::class.java)
            intent.putExtra("Title", titles[position])
            intent.putExtra("Result", res)
            startActivity(intent)
        }
    }

    fun Request(URL:String, recyclerView: RecyclerView){
         titles.clear()
         imagesLinks.clear()
         val request = Request.Builder().url(URL).build()
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
                            val image = jO.getJSONObject(i).get("image").toString()
                            imagesLinks.add(image)
                            titles.add(name)
                            i++
                            runOnUiThread {
                                recyclerView.adapter = RecyclerViewAdapter(titles, imagesLinks,this@ListActivity)
                            }

                        } catch (e: Exception) {
                            bol = false
                        }
                    }
                }
                else{
                    Toast.makeText(this@ListActivity,"По вашему запросу нет результата",Toast.LENGTH_LONG).show()
                }

            }

        })
    }
}