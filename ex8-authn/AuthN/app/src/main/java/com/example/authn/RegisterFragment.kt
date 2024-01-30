package com.example.authn

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

class RegisterFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        val registerButton = view.findViewById<Button>(R.id.button_register)
        registerButton.setOnClickListener {
            registerHandler()
        }

        val switchLoginButton = view.findViewById<Button>(R.id.button_switch_login)
        switchLoginButton.setOnClickListener {
            switchLoginHandler()
        }

        return view
    }

    private fun registerHandler() {
        val emailEditText = requireView().findViewById<EditText>(R.id.input_email)
        val passwordEditText = requireView().findViewById<EditText>(R.id.input_password)

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (!isValidEmail(email)) {
            Toast.makeText(
                context,
                "Invalid email.",
                Toast.LENGTH_SHORT,
            ).show()
            return
        }

        if (!isValidPassword(password)) {
            Toast.makeText(
                context,
                "Invalid password (length<8).",
                Toast.LENGTH_SHORT,
            ).show()
            return
        }

        val activity = requireActivity()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.i("ABC", "createUserWithEmail:success")
                } else {
                    Log.i("ABC", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context,
                        "Authentication failed (${task.exception?.message}).",
                        Toast.LENGTH_LONG,
                    ).show()
                }
            }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

    private fun switchLoginHandler() {
        val fragment = LoginFragment()
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}