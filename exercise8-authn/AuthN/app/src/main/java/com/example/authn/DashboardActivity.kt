package com.example.authn

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
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

        val providersTextView = findViewById<TextView>(R.id.text_providers)
        var providersText = ""
        if (user != null) {
            for (p in user.providerData) {
                providersText += p.providerId + ", "
            }
            providersText = providersText.dropLast(2)
        }
        providersTextView.text = providersText

        val logoutButton = findViewById<Button>(R.id.button_logout)
        logoutButton.setOnClickListener {
            logoutHandler()
        }
    }

    private fun logoutHandler() {
        logoutFirebase()
        logoutGoogle()
        logoutFacebook()

        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun logoutFirebase() {
        auth.signOut()
    }

    private fun logoutGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut()
    }

    private fun logoutFacebook() {
        LoginManager.getInstance().logOut();
    }

}