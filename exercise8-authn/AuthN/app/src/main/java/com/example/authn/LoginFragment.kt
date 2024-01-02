package com.example.authn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val loginButton = view.findViewById<Button>(R.id.button_login)
        loginButton.setOnClickListener {
            loginHandler()
        }

        val switchRegisterButton = view.findViewById<Button>(R.id.button_switch_register)
        switchRegisterButton.setOnClickListener {
            switchRegisterHandler()
        }

        return view
    }


    private fun loginHandler() {
        val emailEditText = requireView().findViewById<EditText>(R.id.input_email)
        val passwordEditText = requireView().findViewById<EditText>(R.id.input_password)

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                context,
                "Please fill in email and password.",
                Toast.LENGTH_SHORT,
            ).show()
            return
        }

        val activity = requireActivity()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.i("ABC", "signInWithEmail:success", task.exception)

                    val intent = Intent(activity, DashboardActivity::class.java)
                    startActivity(intent)
                    activity.finish()
                } else {
                    Log.w("ABC", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun switchRegisterHandler() {
        val fragment = RegisterFragment()
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}