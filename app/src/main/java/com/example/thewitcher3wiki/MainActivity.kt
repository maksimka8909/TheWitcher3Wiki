package com.example.thewitcher3wiki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = Intent(this@MainActivity,ListActivity::class.java);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnArmor.setOnClickListener {
            intent.putExtra("Result","equipment/armor")
            startActivity(intent)
        }
        btnSwords.setOnClickListener {
            intent.putExtra("Result","equipment/sword")
            startActivity(intent)
        }
        btnDecotions.setOnClickListener {
            intent.putExtra("Result","potion/decotions")
            startActivity(intent)
        }
        btnElixirs.setOnClickListener {
            intent.putExtra("Result","potion/elixirs")
            startActivity(intent)
        }
        btnBeast.setOnClickListener {
            intent.putExtra("Result","beast")
            startActivity(intent)
        }
        btnCharacters.setOnClickListener {
            intent.putExtra("Result","character")
            startActivity(intent)
        }
        btnLocations.setOnClickListener {
            intent.putExtra("Result","location")
            startActivity(intent)
        }
    }
}