package com.example.authn

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val emailTextView = findViewById<TextView>(R.id.text_email)
        emailTextView.text = "johndoe@mail.com"

        val logoutButton = findViewById<Button>(R.id.button_logout)
        logoutButton.setOnClickListener {
            println("LOGOUT")
        }
    }
    
}