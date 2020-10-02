package com.noobcode.teamscopetodo.main.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.noobcode.teamscopetodo.R
import com.noobcode.teamscopetodo.home.activity.HomeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        var intent: Intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}