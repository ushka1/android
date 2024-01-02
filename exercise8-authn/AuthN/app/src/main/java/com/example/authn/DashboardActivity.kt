package com.example.authn

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        val emailTextView = findViewById<TextView>(R.id.text_email)
        emailTextView.text = user?.email ?: "-"

        val logoutButton = findViewById<Button>(R.id.button_logout)
        logoutButton.setOnClickListener {
            logoutHandler()
        }
    }

    private fun logoutHandler() {
        auth.signOut()
        
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

}